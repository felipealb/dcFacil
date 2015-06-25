# coding:utf-8
#Created by felipe on 25/06/15 - 16:26.
__author__ = 'felipe'

import os
import hashlib

class Arqv:
	def __init__(self,caminho):
		self.caminho=caminho
		self.nome=caminho.split('/')[-1]
		self.hasher=hashlib.md5()

	def calculaHash(self):
		arqv=open(self.caminho)
		self.hasher.update(arqv.read().encode())
		arqv.close()

	def getHash(self):
		self.calculaHash()
		return self.hasher.hexdigest()

class Principal:
	def __init__(self):
		print("Hasher...")
		local='./XML/'
		# local='/home/ic/felipe.alb/public_html/XML/'
		lsNomes=os.listdir(local)
		lsArqs=[]
		xmlFile=open(local+'arquivos.xml','w')
		xmlFile.write('<?xml version="1.0" encoding="utf-8"?>\n')
		out='<hashes>\n'
		for arq in lsNomes:
			lsArqs.append(Arqv(local+arq))

		for i in lsArqs:
			out+='\t<arquivo> '+i.nome+' </arquivo>\n'
			out+='\t\t<md5> '+i.getHash()+' </md5>\n'
		out+='</hashes>'
		xmlFile.write(out)
		xmlFile.close()

if __name__=="__main__":
	m=Principal()
