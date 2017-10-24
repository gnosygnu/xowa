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
package gplx.core.stores.xmls; import gplx.*; import gplx.core.*; import gplx.core.stores.*;
import gplx.langs.xmls.*; /*Xpath_*/
public class XmlDataRdr extends DataRdr_base implements DataRdr {
	@Override public String NameOfNode() {return nde.Name();} public String To_str() {return nde.Xml_outer();}
	@Override public int FieldCount() {return nde.Atrs() == null ? 0 : nde.Atrs().Count();} // nde.Attributes == null when nde is XmlText; ex: <node>val</node>
	@Override public String KeyAt(int i) {return nde.Atrs().Get_at(i).Name();}
	@Override public Object ReadAt(int i) {
		XmlAtr attrib = nde.Atrs().Get_at(i);
		return (attrib == null) ? null : attrib.Value();
	}
	@Override public Object Read(String key) {
		return nde.Atrs().FetchValOr(key, null);
	}
	public boolean MoveNextPeer() {
		if (++pos >= peerList.Count()){	// moved out Of range
			nde = null;
			return false; 
		}
		nde = peerList.Get_at(pos);
		return true;
	}
	@Override public DataRdr Subs() {
		XmlNdeList list = Xpath_.SelectElements(nde);
		XmlDataRdr rv = new XmlDataRdr();
		rv.ctor_(list, null);
		return rv;
	}
	@Override public DataRdr Subs_byName_moveFirst(String name) {
		DataRdr subRdr = Subs_byName(name);
		boolean hasFirst = subRdr.MoveNextPeer();
		return (hasFirst) ? subRdr : DataRdr_.Null;
	}
	public DataRdr Subs_byName(String name) {
		XmlNdeList list = Xpath_.SelectAll(nde, name);
		XmlDataRdr rv = new XmlDataRdr();
		rv.ctor_(list, null);
		return rv;
	}		
	public void Rls() {nde = null; peerList = null;}
	public String NodeValue_get() {
		if (nde.SubNdes().Count() != 1) return "";
		XmlNde sub = nde.SubNdes().Get_at(0);
		return (sub.NdeType_textOrEntityReference()) ? sub.Text_inner() : "";
	}
	public String Node_OuterXml() {return nde.Xml_outer();}
	@Override public SrlMgr SrlMgr_new(Object o) {return new XmlDataRdr();}
	void LoadString(String raw) {
		XmlDoc xdoc = XmlDoc_.parse(raw);
		XmlNdeList list = Xpath_.SelectElements(xdoc.Root());
		ctor_(list, xdoc.Root());
	}
	void ctor_(XmlNdeList peerList, XmlNde nde) {
		this.peerList = peerList; this.nde = nde; pos = -1;
	}
	
	XmlNde nde = null;
	XmlNdeList peerList = null; int pos = -1;
	@gplx.Internal protected XmlDataRdr(String raw) {this.LoadString(raw); this.Parse_set(true);}
	XmlDataRdr() {this.Parse_set(true);}
}
