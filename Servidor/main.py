# coding:utf-8
#Created by felipe on 25/06/15 - 17:10.
__author__ = 'felipe'

import hasher,webParser
print("Solicitando:")
c=webParser.Controlador()
c.procuraNoticias()
c.pegaCardapio()
hasher.Principal()
print("Concluído!")