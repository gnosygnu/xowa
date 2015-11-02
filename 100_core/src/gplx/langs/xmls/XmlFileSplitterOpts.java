/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012 gnosygnu@gmail.com

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
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
