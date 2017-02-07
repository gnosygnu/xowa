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
package gplx.xowa.xtns.wbases.imports; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.wbases.*;
import org.junit.*;
import gplx.xowa.wikis.*; import gplx.xowa.wikis.tdbs.*;
public class Xob_wdata_pid_base_tst {
	gplx.xowa.bldrs.Xob_fxt fxt = new gplx.xowa.bldrs.Xob_fxt().Ctor_mem();
	Io_url reg_(Xowe_wiki wdata, String wiki) {return Wdata_idx_wtr.dir_pid_(wdata, wiki).GenSubFil(Xotdb_dir_info_.Name_reg_fil);}
	Io_url ttl_(Xowe_wiki wdata, String wiki, int fil_id) {
		Io_url root = Wdata_idx_wtr.dir_pid_(wdata, wiki);
		return Xotdb_fsys_mgr.Url_fil(root, fil_id, Xotdb_dir_info_.Bry_xdat);
	}
	@Test   public void Basic() {
		fxt.Wiki().Ns_mgr().Add_new(Wdata_wiki_mgr.Ns_property, "Property");
		fxt.doc_ary_
		(	fxt.doc_wo_date_(2, "Property:P2", json_("p2", "label", String_.Ary("en", "p2_en", "fr", "p2_fr")))
		,	fxt.doc_wo_date_(1, "Property:P1", json_("p1", "label", String_.Ary("en", "p1_en", "fr", "p1_fr")))
		)
		.Fil_expd(ttl_(fxt.Wiki(), "en", 0)
		,	"!!!!*|!!!!*|"
		,	"p1_en|p1"
		,	"p2_en|p2"
		,	""
		)
		.Fil_expd
		(	reg_(fxt.Wiki(), "en")
		,	"0|p1_en|p2_en|2"
		,	""
		)
		.Fil_expd(ttl_(fxt.Wiki(), "fr", 0)
		,	"!!!!*|!!!!*|"
		,	"p1_fr|p1"
		,	"p2_fr|p2"
		,	""
		)
		.Fil_expd
		(	reg_(fxt.Wiki(), "fr")
		,	"0|p1_fr|p2_fr|2"
		,	""
		)
		.Run(new Xob_wdata_pid_txt().Ctor(fxt.Bldr(), this.fxt.Wiki()))
		;
	}
	public static String json_(String entity_id, String grp_key, String[] grp_vals) {
		Bry_bfr bfr = Bry_bfr_.New();
		bfr.Add_str_a7("{ 'entity':'").Add_str_u8(entity_id).Add_byte(Byte_ascii.Apos).Add_byte_nl();
		bfr.Add_str_a7(", 'datatype':'commonsMedia'\n");
		bfr.Add_str_a7(", '").Add_str_u8(grp_key).Add_str_a7("':").Add_byte_nl();
		int len = grp_vals.length;
		for (int i = 0; i < len; i += 2) {
			bfr.Add_byte_repeat(Byte_ascii.Space, 2);
			bfr.Add_byte(i == 0 ? Byte_ascii.Curly_bgn : Byte_ascii.Comma).Add_byte(Byte_ascii.Space);			
			bfr.Add_byte(Byte_ascii.Apos).Add_str_u8(grp_vals[i    ]).Add_byte(Byte_ascii.Apos).Add_byte(Byte_ascii.Colon);
			bfr.Add_byte(Byte_ascii.Apos).Add_str_u8(grp_vals[i + 1]).Add_byte(Byte_ascii.Apos).Add_byte_nl();
		}			
		bfr.Add_str_a7("  }").Add_byte_nl();
		bfr.Add_str_a7("}").Add_byte_nl();
		return String_.Replace(bfr.To_str_and_clear(), "'", "\""); 
	}
}
