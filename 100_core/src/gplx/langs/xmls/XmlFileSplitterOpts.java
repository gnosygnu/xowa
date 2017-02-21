/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2017 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.langs.xmls; import gplx.*; import gplx.langs.*;
public class XmlFileSplitterOpts {
	public int FileSizeMax() {return fileSizeMax;} public XmlFileSplitterOpts FileSizeMax_(int v) {fileSizeMax = v; return this;} int fileSizeMax = 1024 * 1024;
	public String[] XmlNames() {return xmlNames;} public XmlFileSplitterOpts XmlNames_(String... v) {xmlNames = v; return this;} private String[] xmlNames;
	public String XmlBgn() {return xmlBgn;} public XmlFileSplitterOpts XmlBgn_(String v) {xmlBgn = v; return this;} private String xmlBgn;
	public String XmlEnd() {return xmlEnd;} public XmlFileSplitterOpts XmlEnd_(String v) {xmlEnd = v; return this;} private String xmlEnd;
	public Io_url PartDir() {return partDir;} public XmlFileSplitterOpts PartDir_(Io_url v) {partDir = v; return this;} Io_url partDir;
	public String StatusFmt() {return statusFmt;} public XmlFileSplitterOpts StatusFmt_(String v) {statusFmt = v; return this;} private String statusFmt = "splitting {0}";
	public HierStrBldr Namer() {return namer;} HierStrBldr namer = new HierStrBldr();
}
