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
package gplx.xowa.xtns.wdatas; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import org.junit.*;
import gplx.json.*;
public class Wdata_doc_parser_tst {
	@Test  public void Invalid_type__bad() {	// wikidata flags several entries as "bad"; https://www.wikidata.org/wiki/Wikidata:Project_chat/Archive/2013/10
		Json_doc jdoc = Json_doc.new_apos_concat_nl
		(	"{ 'entity':['item',2]"
		,	", 'claims':"
		,	"  ["
		,	"    { 'm':"
		,	"      [ 'value'"
		,	"      , 373"
		,	"      , 'bad'"
		,	"      ,"
		,	"        { 'latitude':1"
		,	"        , 'longitude':2"
		,	"        , 'altitude':null"
		,	"        , 'globe':null"
		,	"        , 'precision':1"
		,	"        }"
		,	"      ]"
		,	"    }"
		,	"  ]"
		,	"}"
		);
		Wdata_doc_parser parser = new Wdata_doc_parser(Gfo_usr_dlg_.Null);
		parser.Bld_props(jdoc);	// make sure it doesn't fail
	}
}
