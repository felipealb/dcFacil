# coding:utf-8
#Created by flowp on 04/06/15 - 00:45.
__author__ = 'flowp'
import urllib.request
from html.parser import HTMLParser

#Entidades
class paginaWeb(HTMLParser):

	def __init__(self,url):
		self.url=url
		self.conteudo=None

	def getPagina(self):
		"""
		Faz a consulta da pagina na internet
		:return: None
		"""
		response=urllib.request.urlopen(self.url)
		saida=response.read()
		self.conteudo=saida.decode()

	def getPaginaHTML(self):
		"""
		Retorna pagina salva no objeto
		:return: pagina HTML
		"""
		return self.conteudo

class computacaoParser(HTMLParser):
	resultados=[]
	d=dict()
	palavrasChave=['computacao','computação']
	item=0
	def handle_starttag(self, tag, attrs):
		#@todo Otimização e limpeza desse código
		# print("encontrada tag",tag)
		if tag=='a':
			for atributo in attrs:
				# print(atributo)
				if atributo[0]=='href':
					for palavra in self.palavrasChave:
						# print("procurando",palavra,"em",atributo[1])
						if palavra in atributo[1]:
							# print("http://ufc.br"+atributo[1])
							self.resultados.append("http://ufc.br"+atributo[1])
							self.d[self.item]=[None,"http://ufc.br"+atributo[1]]

	def handle_data(self, data):
		#@todo Otimização e limpeza desse código
		# print("encontrados dados",data)
		dataLowerCase=str(data).lower()
		for palavra in self.palavrasChave:
			if palavra in dataLowerCase:
				# print(dataLowerCase)
				self.resultados.append(str(data).strip())
				self.d[self.item][0]=str(data).strip()

	def handle_endtag(self, tag):
		self.item+=1

#Controlador
class controller:
	def __init__(self):
		self.noticias=[]
		self.urls=[]
		self.urlNoticiasUFC="http://ufc.br/noticias"
		self.urlCardapioRU="http://www.ufc.br/servidores/restaurante-universitario/2444-cardapio-do-restaurante-universitario"

	def procuraNoticias(self,url):
		pagina=paginaWeb(url)
		pagina.getPagina()
		parser=computacaoParser(strict=False)
		parser.feed(pagina.getPaginaHTML())
		return parser.d

if __name__=="__main__":

	c=controller()
	dicionario=c.procuraNoticias(c.urlNoticiasUFC)
	print(dicionario.keys(),dicionario.values())