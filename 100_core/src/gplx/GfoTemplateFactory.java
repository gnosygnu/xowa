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
package gplx;
public class GfoTemplateFactory implements Gfo_invk {
	public void Reg(String key, GfoTemplate template) {hash.Add(key, template);}
	public Object Make(String key) {
		GfoTemplate template = (GfoTemplate)hash.Get_by(key);
		return template.NewCopy(template);
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		ctx.Match(k, k);
		Object o = hash.Get_by(k);
		return o == null ? Gfo_invk_.Rv_unhandled : o;
	}
        public static final    GfoTemplateFactory Instance = new GfoTemplateFactory(); GfoTemplateFactory() {}
	Hash_adp hash = Hash_adp_.New();
}
