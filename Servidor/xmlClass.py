#coding:utf-8
#Created by flowp on 24/06/15 - 22:48.
__author__ = 'flowp'


class NoXML:
	def __init__(self,tagNome,conteudo):
		self.tag=tagNome
		self.conteudo=conteudo
		self.itens=[]

	def incrementa(self,item):
		self.itens.append(item)

	def setConteudo(self,conteudo):
		self.conteudo=conteudo

	def __str__(self):
		return self.tag

	def __repr__(self):
		return self.tag

	def show(self):
		if type(self.conteudo) is list:
			print('\t\t\t\té uma lista...')
			for i in self.conteudo:
				print(i)
		elif type(self.conteudo) is NoXML:
			print('\t\t\t\té um nó XML...')
			# print('\t<'+self.tag+'>\n\t'+self.conteudo)
			print('\t<'+self.tag+'>\n\t')
			self.conteudo.show()
			print('\t</'+self.tag+'>\n\t')
		else:
			print('\t<'+self.conteudo.__str__()+'>\n\t')


class ArqvXML:
	def __init__(self,raiz):
		self.pilha=[]
		self.cabecalho='<?xml version="1.0" encoding="utf-8"?>'
		self.cabecalho+='\n<'+str(raiz)+'>'

	def inclui(self,classe,conteudo):
		self.pilha.append(classe)
		if conteudo is not None:
			self.pilha.append(conteudo)

	def empilha(self,classe,conteudo):
		self.pilha.append([classe])
		if conteudo is not None:
			self.pilha.append((conteudo))

	def monta(self):
		for classe in self.pilha:
			if type(classe) is list:
				print('\t<'+classe+'>')

if __name__=="__main__":
	a=NoXML('a','conteudo A')
	b=NoXML('b','conteudo B')
	c=NoXML('c','conteudo C')
	d=NoXML('d','conteudo D')

	a.setConteudo(b)
	b.incrementa(c)
	b.incrementa(d)
	a.show()
