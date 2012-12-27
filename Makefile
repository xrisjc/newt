# $Id: Makefile,v 1.4 2003/12/28 03:51:33 chris Exp chris $
#
# $Revision: 1.4 $
#
# $Log: Makefile,v $
# Revision 1.4  2003/12/28 03:51:33  chris
# Changed spelling of licence.txt to license.txt
#
# Revision 1.3  2003/12/26 03:25:17  chris
# Added licence.txt to each of the jar builds.
#
# Revision 1.2  2003/12/26 03:14:51  chris
# Added an executable and sour jar file.
#
# Revision 1.1  2003/12/25 05:30:02  chris
# Initial revision
#
#
newt:
	javac Newt.java

clean:
	rm *class *jar *~

execjar: newt 
	jar -cfm newt.jar manifest *class license.txt

srcjar: newt
	jar -cf newt-src.jar *java manifest Makefile license.txt

execsrcjar:
	jar -cfm newt-exec-src.jar manifest *class *java manifest Makefile license.txt

alljar: execjar srcjar execsrcjar