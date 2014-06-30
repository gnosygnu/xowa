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
package gplx.stores; import gplx.*;
public class GfoNdeRdr_ {
	public static GfoNdeRdr kvs_(KeyValList kvList) {
		GfoFldList flds = GfoFldList_.new_();
		int pairsLen = kvList.Count();
		Object[] vals = new Object[pairsLen];
		for (int i = 0; i < pairsLen; i++) {
			KeyVal pair = kvList.GetAt(i);
			flds.Add(pair.Key(), StringClassXtn._);
			vals[i] = pair.Val_to_str_or_empty();
		}
		GfoNde nde = GfoNde_.vals_(flds, vals);
		return root_(nde, true);
	}
	public static GfoNdeRdr root_parseNot_(GfoNde root) {return root_(root, true);}
	public static GfoNdeRdr root_(GfoNde root, boolean parse) {
		DataRdr_mem rv = DataRdr_mem.new_(root, root.Flds(), root.Subs()); rv.Parse_set(parse);
		return rv;
	}
	public static GfoNdeRdr leaf_(GfoNde cur, boolean parse) {
		DataRdr_mem rv = DataRdr_mem.new_(cur, cur.Flds(), GfoNdeList_.Null); rv.Parse_set(parse);
		return rv;
	}
	public static GfoNdeRdr peers_(GfoNdeList peers, boolean parse) {
		GfoFldList flds = peers.Count() == 0 ? GfoFldList_.Null : peers.FetchAt_asGfoNde(0).Flds();
		DataRdr_mem rv = DataRdr_mem.new_(null, flds, peers); rv.Parse_set(parse);
		return rv;
	}
	public static GfoNdeRdr as_(Object obj) {return obj instanceof GfoNdeRdr ? (GfoNdeRdr)obj : null;}
	public static GfoNdeRdr cast_(Object obj) {try {return (GfoNdeRdr)obj;} catch(Exception exc) {throw Err_.type_mismatch_exc_(exc, GfoNdeRdr.class, obj);}}
}
