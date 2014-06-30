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
package gplx.xowa.xtns.listings; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import gplx.html.*;
import gplx.xowa.wikis.*;
public class Listing_xtn_mgr extends Xox_mgr_base {
	@Override public boolean Enabled_default() {return false;}
	@Override public byte[] Xtn_key() {return Xtn_key_static;} public static final byte[] Xtn_key_static = Bry_.new_ascii_("listings");
	@Override public Xox_mgr Clone_new() {return new Listing_xtn_mgr();}
	@Override public void Xtn_init_by_wiki(Xow_wiki wiki) {
		if (!Enabled()) return;
		this.Reset(wiki);
	}
	@gplx.Internal protected Html_wtr Hwtr() {return hwtr;} private Html_wtr hwtr;
	public void Clear() {
		listings_template = phone_symbol = tollfree_symbol = fax_symbol = email_symbol = null;
		checkin_msg = checkout_msg = position_template = position_text = null;
	}
	private void Reset(Xow_wiki wiki) {
		hwtr = new Html_wtr();
		Xop_ctx sub_ctx = Xop_ctx.new_sub_(wiki);
		listings_template		= Load_txt(wiki, sub_ctx, "listings-template");
		phone_symbol			= Load_txt(wiki, sub_ctx, "listings-phone-symbol", "listings-phone");
		tollfree_symbol			= Load_txt(wiki, sub_ctx, "listings-tollfree-symbol", "listings-tollfree");
		fax_symbol				= Load_txt(wiki, sub_ctx, "listings-fax-symbol", "listings-fax");
		email_symbol			= Load_txt(wiki, sub_ctx, "listings-email-symbol", "listings-email");
		checkin_msg				= Load_msg(wiki, sub_ctx, "listings-checkin");
		checkout_msg			= Load_msg(wiki, sub_ctx, "listings-checkout");
		position_template		= Load_msg(wiki, sub_ctx, "listings-position-template");	// EX: formats 11 12 to "template_name|11|12"
		position_text			= Load_msg(wiki, sub_ctx, "listings-position");				// EX: formats output of "{{template_name|11|12}}" to "message $output"
	}
	public byte[] Listings_template() {return listings_template;} private byte[] listings_template;
	public byte[] Phone_symbol() {return phone_symbol;} private byte[] phone_symbol;
	public byte[] Tollfree_symbol() {return tollfree_symbol;} private byte[] tollfree_symbol;
	public byte[] Fax_symbol() {return fax_symbol;} private byte[] fax_symbol;
	public byte[] Email_symbol() {return email_symbol;} private byte[] email_symbol;
	public Xol_msg_itm Checkin_msg() {return checkin_msg;} private Xol_msg_itm checkin_msg;
	public Xol_msg_itm Checkout_msg() {return checkout_msg;} private Xol_msg_itm checkout_msg;
	public Xol_msg_itm Position_template() {return position_template;} private Xol_msg_itm position_template;
	public Xol_msg_itm Position_text() {return position_text;} private Xol_msg_itm position_text;
	private byte[] Load_txt(Xow_wiki wiki, Xop_ctx sub_ctx, String symbol_ttl, String template_ttl) {
		byte[] symbol_text = Load_txt(wiki, sub_ctx, symbol_ttl);
		byte[] template_text = Load_txt(wiki, sub_ctx, template_ttl);
		byte[] rv = null;
		if (symbol_text != null) {
			hwtr.Nde_full_atrs(Listing_xnde.Tag_abbr, symbol_text, true
				, Listing_xnde.Atr_a_title, Html_utl.Escape_html_as_bry(template_text)
				);
			rv = hwtr.X_to_bry_and_clear();
		}
		else {
			rv = template_text;
		}
		return rv;
	}
	private byte[] Load_txt(Xow_wiki wiki, Xop_ctx sub_ctx, String ttl) {
		byte[] rv = wiki.Msg_mgr().Val_by_key_obj(Bry_.new_utf8_(ttl)); if (Bry_.Len_eq_0(rv)) return null;	// ttl does not exist; note that msg_mgr returns "" for missing values
		rv = wiki.Parser().Parse_text_to_html(sub_ctx, rv);
		rv = Html_utl.Escape_html_as_bry(rv);
		return rv;
	}
	private Xol_msg_itm Load_msg(Xow_wiki wiki, Xop_ctx sub_ctx, String ttl) {
		return wiki.Msg_mgr().Find_or_null(Bry_.new_utf8_(ttl)); 
	}
	public static void Create_msgs() {}
}
