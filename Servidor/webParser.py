# coding:utf-8
#Created by flowp on 04/06/15 - 00:45.
__author__ = 'flowp'
import urllib.request

#Entidades
class paginaWeb():
	def __init__(self,url,nome):
		self.url=url
		self.nome=nome
		self.conteudo=None

	def getPage(self):
		#@todo tratar como objetos, e não arquivos
		arq=open(self.nome+".html","w")
		response=urllib.request.urlopen(self.url)
		saida=response.read()
		self.conteudo=saida.decode()
		arq.writelines(saida.decode())
		arq.close()

	def leArquivoHTML(self):
		for linhas in self.conteudo.split("\n"):
			if "noticias-de-2015" in linhas: print(linhas)
			#@todo corrigir linha acima para fazer grep no texto

if __name__=="__main__":
	urlCardapioRU="http://www.ufc.br/servidores/restaurante-universitario/2444-cardapio-do-restaurante-universitario"
	urlNoticiasUFC="http://ufc.br/noticias"

	pg1=paginaWeb(urlCardapioRU,"cardapio")
	pg2=paginaWeb(urlNoticiasUFC,"noticias")
	noticias="noticias"
	cardapio="cardapio.html"


	pg1.getPage()
	pg1.leArquivoHTML()