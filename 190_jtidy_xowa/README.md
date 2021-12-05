jtidy_xowa
==========

jtidy_xowa is a fork of http://jtidy.sourceforge.net. It is intended to closely emulate tidy (http://tidy.sourceforge.net). for XOWA: an offline Wikipedia application.

Here are some examples of fixes / changes:

* JTidy always converts "&lt;hr/>" to "&lt;br>&lt;br>".
* JTidy indents content inside a textarea when setIndentContent is true
* JTidy sometimes behaves strangely for setEncloseBlockText
* JTidy leaks memory when called repeatedly (In XOWA's case, jtidy_xowa now runs through 5.5 million pages without running out of memory)

In total, there are approximately 150+ changes to JTidy.
