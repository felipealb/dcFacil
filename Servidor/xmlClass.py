#coding:utf-8
#Created by flowp on 24/06/15 - 22:48.
__author__ = 'flowp'


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
	a=ArqvXML()
