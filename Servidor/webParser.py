# coding:utf-8
# Created by flowp on 04/06/15 - 00:45.
__author__ = 'flowp'
import urllib.request
from html.parser import HTMLParser

# Entidades
class paginaWeb(HTMLParser):
	def __init__(self, url):
		self.url = url
		self.conteudo = None

	def getPagina(self):
		"""
		Faz a consulta da pagina na internet
		:return: None
		"""
		response = urllib.request.urlopen(self.url)
		saida = response.read()
		self.conteudo = saida.decode()

	def getPaginaHTML(self):
		"""
		Retorna pagina salva no objeto
		:return: pagina HTML
		"""
		return self.conteudo

class computacaoParser(HTMLParser):
	resultados = []
	d = dict()
	palavrasChave = ['computacao', 'computação', 'Computação', 'prof']  # @todo remover 'prof'
	item = 0

	def handle_starttag(self, tag, attrs):
		# @todo Otimização e limpeza desse código
		# print("encontrada tag",tag)
		if tag == 'a':
			for atributo in attrs:
				# print(atributo)
				if atributo[0] == 'href':
					for palavra in self.palavrasChave:
						# print("procurando",palavra,"em",atributo[1])
						if palavra in atributo[1]:
							# print("http://ufc.br"+atributo[1])
							self.resultados.append("http://ufc.br" + atributo[1])
							self.d[self.item] = [None, "http://ufc.br" + atributo[1]]

	def handle_data(self, data):
		# @todo Otimização e limpeza desse código
		# print("encontrados dados",data)
		dataLowerCase = str(data).lower()
		for palavra in self.palavrasChave:
			if palavra in dataLowerCase:
				# print(dataLowerCase)
				self.resultados.append(str(data).strip())
				self.d[self.item][0] = str(data).strip()

	def handle_endtag(self, tag):
		self.item += 1

class ruParser(HTMLParser):
	d = dict()
	creating, startRead = False, False
	item, itens = ['DESJEJUM'], []

	def createObj(self, estado, conteudo=None):
		if self.startRead:
			if estado == "ini":
				self.creating = True
				self.item = []
			elif estado == "fim":
				self.creating = False
				self.itens.append(self.item)
			else:
				self.item.append(conteudo)

	def handle_starttag(self, tag, attrs):
		if tag == 'td' and self.startRead:
			# print('<')
			self.createObj('ini')

	def handle_data(self, data):
		data = data.strip('-').strip()
		if data == 'DESJEJUM':
			self.startRead = True
		elif data == 'Atenção':
			self.startRead = False
		if self.creating and self.startRead:
			if data != '':
				# print('\t',data)
				self.createObj("cont", data)

	def handle_endtag(self, tag):
		if tag == 'td' and self.startRead:
			# print('>')
			self.createObj('fim')

# Controlador
class controller:
	def __init__(self):
		self.noticias = []
		self.urls = []
		self.urlNoticiasUFC = "http://ufc.br/noticias"
		self.urlCardapioRU = "http://www.ufc.br/servidores/restaurante-universitario/2444-cardapio-do-restaurante-universitario"

	def procuraNoticias(self):
		pagina = paginaWeb(self.urlNoticiasUFC)
		pagina.getPagina()
		parser = computacaoParser(strict=False)
		parser.feed(pagina.getPaginaHTML())
		self.salvaResultadosNoticas(parser.d)

	def salvaResultadosNoticas(self, dicionario):
		# saida=open("/home/ic/felipe.alb/public_html/XML/news.xml",'w')
		saida = open("/home/flowp/repo/dcFacil/Servidor/tmp.xml", 'w')
		saida.write('<?xml version="1.0" encoding="utf-8"?>\n')
		saida.write('<news>\n')
		for key in dicionario:
			string = '<noticia link="' + dicionario[key][1] + '">' + dicionario[key][0] + '</noticia>\n'
			print(string)
			saida.write(string)
		saida.write('</news>\n')
		saida.close()

	def pegaCardapio(self):
		pagina = paginaWeb(self.urlCardapioRU)
		pagina.getPagina()
		parser = ruParser(strict=False)
		parser.feed(pagina.getPaginaHTML())

		café = [[], [], [], [], [], []]
		almoço = [[], [], [], [], [], []]
		jantar = [[], [], [], [], [], []]
		cont = 0
		for i in parser.itens:
			if i[0] in ['DESJEJUM', 'ALMOÇO', 'JANTAR']:
				refeicao = i[0]

			if refeicao == 'DESJEJUM':
				café[cont].append(i)
				cont += 1
				if cont > 5: cont = 0

			elif refeicao == 'ALMOÇO':
				almoço[cont].append(i)
				cont += 1
				if cont > 5: cont = 0

			elif refeicao == 'JANTAR':
				jantar[cont].append(i)
				cont += 1
				if cont > 5: cont = 0

		# for i in café:
		# 	print(i)
		# print()
		# for i in almoço:
		# 	print(i)
		# print()
		# for i in jantar:
		# 	print(i)
		self.geraXML(café, almoço, jantar)

	def geraXML(self, cafe, almoco, janta):
		out='<?xml version="1.0" encoding="utf-8"?>\n<cardapio>\n'
		indice=0
		dias_semana=['segunda','terca','quarta','quinta','sexta']
		refeicao=cafe[0][0][0]

		#Café
		for dia in dias_semana:
			indice+=1
			bebida=cafe[indice][1][0]
			paes=cafe[indice][2][0]
			frutas=cafe[indice][3][0]
			especial=cafe[indice][4][0]

			out+='<'+dia+'>\n'
			out+='\t<'+refeicao+'>\n'
			out+='\t\t<bebida> '+bebida+' </bebida>\n'
			out+='\t\t<paes> '+paes+' </paes>\n'
			out+='\t\t<frutas> '+frutas+' </frutas>\n'
			out+='\t\t<especial> '+especial+' </especial>\n'
			out+='\t</'+refeicao+'>\n'

			#Almoço
			refeicao=almoco[0][0][0]
			principal=almoco[indice][1][0]
			vegetariano=almoco[indice][2][0]
			salada=almoco[indice][3][0]
			guarnicao=almoco[indice][4][0]
			acompanhamento=almoco[indice][5][0]
			suco=almoco[indice][6][0]
			sobremesa=almoco[indice][7][0]

			out+='\t<'+refeicao+'>\n'
			out+='\t\t<principal> '+principal+' </principal>\n'
			out+='\t\t<vegetariano> '+vegetariano+' </vegetariano>\n'
			out+='\t\t<salada> '+salada+' </salada>\n'
			out+='\t\t<guarnicao> '+guarnicao+' </guarnicao>\n'
			out+='\t\t<acompanhamento> '+acompanhamento+' </acompanhamento>\n'
			out+='\t\t<suco> '+suco+' </suco>\n'
			out+='\t\t<sobremesa> '+sobremesa+' </sobremesa>\n'
			out+='\t</'+refeicao+'>\n'

			#Janta
			refeicao=janta[0][0][0]
			principal=almoco[indice][1][0]
			vegetariano=almoco[indice][2][0]
			salada=almoco[indice][3][0]
			guarnicao=almoco[indice][4][0]
			acompanhamento=almoco[indice][5][0]
			suco=almoco[indice][6][0]
			sobremesa=almoco[indice][7][0]

			out+='\t<'+refeicao+'>\n'
			out+='\t\t<principal> '+principal+' </principal>\n'
			out+='\t\t<vegetariano> '+vegetariano+' </vegetariano>\n'
			out+='\t\t<salada> '+salada+' </salada>\n'
			out+='\t\t<guarnicao> '+guarnicao+' </guarnicao>\n'
			out+='\t\t<acompanhamento> '+acompanhamento+' </acompanhamento>\n'
			out+='\t\t<suco> '+suco+' </suco>\n'
			out+='\t\t<sobremesa> '+sobremesa+' </sobremesa>\n'
			out+='\t</'+refeicao+'>\n'

			out+='\t</'+dia+'>\n'
		out+='</cardapio>'
		print(out)
		s=open('cardapioSAIDA.xml','w')
		s.write(out)
		s.close()


if __name__ == "__main__":
	print("Requisitando informações...")
	c = controller()
	# c.procuraNoticias()
	c.pegaCardapio()
