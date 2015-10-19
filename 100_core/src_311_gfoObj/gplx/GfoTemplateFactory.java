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
package gplx;
public class GfoTemplateFactory implements GfoInvkAble {
	public void Reg(String key, GfoTemplate template) {hash.Add(key, template);}
	public Object Make(String key) {
		GfoTemplate template = (GfoTemplate)hash.Get_by(key);
		return template.NewCopy(template);
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		ctx.Match(k, k);
		Object o = hash.Get_by(k);
		return o == null ? GfoInvkAble_.Rv_unhandled : o;
	}
        public static final GfoTemplateFactory Instance = new GfoTemplateFactory(); GfoTemplateFactory() {}
	Hash_adp hash = Hash_adp_.new_();
}
