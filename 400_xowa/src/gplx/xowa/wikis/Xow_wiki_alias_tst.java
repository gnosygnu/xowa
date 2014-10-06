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
package gplx.xowa.wikis; import gplx.*; import gplx.xowa.*;
import org.junit.*; import gplx.xowa.wikis.*;
public class Xow_wiki_alias_tst {
	Xow_wiki_alias_fxt fxt = new Xow_wiki_alias_fxt();
	@Test  public void Parse()	{fxt.Parse("enwiki-20121201-pages-articles.xml.bz2", "en.wikipedia.org", "20121201", Xow_wiki_alias.Tid_pages_articles);}
	@Test  public void Parse__domain_name() {
		fxt.Parse__domain_name("foundationwiki"			, "wikimediafoundation.org");
		fxt.Parse__domain_name("wikidatawiki"			, "www.wikidata.org");
		fxt.Parse__domain_name("mediawikiwiki"			, "www.mediawiki.org");
		fxt.Parse__domain_name("commonswiki"			, "commons.wikimedia.org");
		fxt.Parse__domain_name("specieswiki"			, "species.wikimedia.org");
		fxt.Parse__domain_name("metawiki"				, "meta.wikimedia.org");
		fxt.Parse__domain_name("incubatorwiki"			, "incubator.wikimedia.org");
		fxt.Parse__domain_name("enwiki"					, "en.wikipedia.org");
		fxt.Parse__domain_name("enwiktionary"			, "en.wiktionary.org");
		fxt.Parse__domain_name("enwikisource"			, "en.wikisource.org");
		fxt.Parse__domain_name("enwikibooks"			, "en.wikibooks.org");
		fxt.Parse__domain_name("enwikiversity"			, "en.wikiversity.org");
		fxt.Parse__domain_name("enwikiquote"			, "en.wikiquote.org");
		fxt.Parse__domain_name("enwikinews"				, "en.wikinews.org");
		fxt.Parse__domain_name("enwikivoyage"			, "en.wikivoyage.org");
	}
	@Test  public void Parse__tid() {
		fxt.Parse__tid("pages-articles.xml"				, Xow_wiki_alias.Tid_pages_articles);
		fxt.Parse__tid("pages-meta-current.xml"			, Xow_wiki_alias.Tid_pages_meta_current);
	}
	@Test  public void Build_alias() {
		fxt.Build_alias("simple.wikipedia.org", "simplewiki");
		fxt.Build_alias("en.wikipedia.org", "enwiki");
		fxt.Build_alias("commons.wikimedia.org", "commonswiki");
	}
	@Test  public void Build_wmf_src_dir() {
		fxt.Build_wmf_src_dir("simplewiki", "latest", "http://dumps.wikimedia.your.org/simplewiki/latest/");
	}
	@Test  public void Build_wmf_src_name() {
		fxt.Build_wmf_src_name("simplewiki", "latest", Xow_wiki_alias.Key_pages_articles, "simplewiki-latest-pages-articles.xml.bz2");
	}
	@Test  public void Build_alias_by_lang_tid() {
		fxt.Build_alias_by_lang_tid("en", Xow_wiki_domain_.Tid_wikipedia, "enwiki");
	}
}
class Xow_wiki_alias_fxt {
	Xow_wiki_alias file = new Xow_wiki_alias();
	public void Parse(String name, String domain, String date, byte tid) {
		Io_url fil = Io_url_.mem_fil_("mem/xowa/temp/" + name);
		file.Fil_(fil).Parse(fil.NameOnly());
		Tfds.Eq(domain	, String_.new_ascii_(file.Domain()));
		Tfds.Eq(date	, file.Date());
		Tfds.Eq(tid		, file.Tid());
	}
	public void Parse__domain_name(String raw_str, String expd) {byte[] raw = Bry_.new_ascii_(raw_str); Tfds.Eq(expd, String_.new_ascii_(Xow_wiki_alias.Parse__domain_name(raw, 0, raw.length)));}
	public void Parse__tid(String raw_str, byte expd) {Tfds.Eq(expd, Xow_wiki_alias.Parse__tid(raw_str));}
	public void Build_alias(String domain_str, String expd) {
		Xow_wiki_domain domain = Xow_wiki_domain_.parse_by_domain(Bry_.new_ascii_(domain_str));
		byte[] actl = Xow_wiki_alias.Build_alias(domain);
		Tfds.Eq(expd, String_.new_ascii_(actl));
	}	private Bry_bfr tmp_bfr = Bry_bfr.reset_(255);
	public void Build_wmf_src_dir(String alias, String date, String expd) {
		byte[] actl = Xob_dump_file_.Bld_dump_dir_url(Bry_.new_ascii_(Xob_dump_file_.Server_your_org), Bry_.new_ascii_(alias), Bry_.new_ascii_(date));
		Tfds.Eq(expd, String_.new_ascii_(actl));
	}
	public void Build_wmf_src_name(String alias, String date, String dump_type, String expd) {
		byte[] actl = Xob_dump_file_.Bld_dump_file_name(Bry_.new_ascii_(alias), Bry_.new_ascii_(date), Bry_.new_ascii_(dump_type), Xob_dump_file_.Ext_xml_bz2);
		Tfds.Eq(expd, String_.new_ascii_(actl));
	}
	public void Build_alias_by_lang_tid(String lang_key, byte wiki_tid, String expd) {
		Xow_wiki_alias.Build_alias_by_lang_tid(tmp_bfr, Bry_.new_ascii_(lang_key), wiki_tid_ref.Val_(wiki_tid));
		Tfds.Eq_bry(Bry_.new_utf8_(expd), tmp_bfr.XtoAryAndClear());
	}	static final Byte_obj_ref wiki_tid_ref = Byte_obj_ref.zero_();
}
