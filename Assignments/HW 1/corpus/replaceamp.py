import os
files=os.listdir("C:/Users/Ganesh/Documents/GitHub/Search/Assignments/HW 1/corpus/corpus")
folder="C:/Users/Ganesh/Documents/GitHub/Search/Assignments/HW 1/corpus/corpus"
for fl in files:
	rfile=[line.replace("&","&amp;") for line in open("/".join([folder,fl]),'r').readlines()]
	wfile=open("1/".join([folder,fl]),'w')
	for line in rfile:
		wfile.write(line)
	wfile.close()
