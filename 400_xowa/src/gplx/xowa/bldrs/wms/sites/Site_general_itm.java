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
package gplx.xowa.bldrs.wms.sites; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.wms.*;
class Site_general_itm implements To_str_able {
	public Site_general_itm Ctor(byte[] main_page, byte[] base_url, byte[] site_name, byte[] logo, byte[] generator
	, byte[] php_version, byte[] php_sapi, byte[] hhvm_version, byte[] db_type, byte[] db_version
	, boolean image_whitelist_enabled, boolean lang_conversion, boolean title_conversion
	, byte[] link_prefix_charset, byte[] link_prefix, byte[] link_trail, byte[] legal_title_chars
	, byte[] git_hash, byte[] git_branch
	, byte[] case_type, byte[] lang, byte[][] fallback, byte[] fallback_8bit_encoding
	, boolean write_api, byte[] time_zone, int time_offset
	, byte[] article_path, byte[] script_path, byte[] script, byte[] variant_article_path
	, byte[] server, byte[] server_name, byte[] wiki_id, byte[] time
	, boolean miser_mode, long max_upload_size, int[] thumb_limits, int[] image_limits, byte[] favicon
	) {
		this.main_page = main_page;
		this.base_url = base_url;
		this.site_name = site_name;
		this.logo = logo;
		this.generator = generator;
		this.php_version = php_version;
		this.php_sapi = php_sapi;
		this.hhvm_version = hhvm_version;
		this.db_type = db_type;
		this.db_version = db_version;
		this.image_whitelist_enabled = image_whitelist_enabled;
		this.lang_conversion = lang_conversion;
		this.title_conversion = title_conversion;
		this.link_prefix_charset = link_prefix_charset;
		this.link_prefix = link_prefix;
		this.link_trail = link_trail;
		this.legal_title_chars = legal_title_chars;
		this.git_hash = git_hash;
		this.git_branch = git_branch;
		this.case_type = case_type;
		this.lang = lang;
		this.fallback = fallback;
		this.fallback_8bit_encoding = fallback_8bit_encoding;
		this.write_api = write_api;
		this.time_zone = time_zone;
		this.time_offset = time_offset;
		this.article_path = article_path;
		this.script_path = script_path;
		this.script = script;
		this.variant_article_path = variant_article_path;
		this.server = server;
		this.server_name = server_name;
		this.wiki_id = wiki_id;
		this.time = time;
		this.miser_mode = miser_mode;
		this.max_upload_size = max_upload_size;
		this.thumb_limits = thumb_limits;
		this.image_limits = image_limits;
		this.favicon = favicon;
		return this;
	}
	public byte[] Main_page() {return main_page;} private byte[] main_page;
	public byte[] Base_url() {return base_url;} private byte[] base_url;
	public byte[] Site_name() {return site_name;} private byte[] site_name;
	public byte[] Logo() {return logo;} private byte[] logo;
	public byte[] Generator() {return generator;} private byte[] generator;
	public byte[] Php_version() {return php_version;} private byte[] php_version;
	public byte[] Php_sapi() {return php_sapi;} private byte[] php_sapi;
	public byte[] Hhvm_version() {return hhvm_version;} private byte[] hhvm_version;
	public byte[] Db_type() {return db_type;} private byte[] db_type;
	public byte[] Db_version() {return db_version;} private byte[] db_version;
	public boolean Image_whitelist_enabled() {return image_whitelist_enabled;} private boolean image_whitelist_enabled;
	public boolean Lang_conversion() {return lang_conversion;} private boolean lang_conversion;
	public boolean Title_conversion() {return title_conversion;} private boolean title_conversion;
	public byte[] Link_prefix_charset() {return link_prefix_charset;} private byte[] link_prefix_charset;
	public byte[] Link_prefix() {return link_prefix;} private byte[] link_prefix;
	public byte[] Link_trail() {return link_trail;} private byte[] link_trail;
	public byte[] Legal_title_chars() {return legal_title_chars;} private byte[] legal_title_chars;
	public byte[] Git_hash() {return git_hash;} private byte[] git_hash;
	public byte[] Git_branch() {return git_branch;} private byte[] git_branch;
	public byte[] Case_type() {return case_type;} private byte[] case_type;
	public byte[] Lang() {return lang;} private byte[] lang;
	public byte[][] Fallback() {return fallback;} private byte[][] fallback;
	public byte[] Fallback_8bit_encoding() {return fallback_8bit_encoding;} private byte[] fallback_8bit_encoding;
	public boolean Write_api() {return write_api;} private boolean write_api;
	public byte[] Time_zone() {return time_zone;} private byte[] time_zone;
	public int Time_offset() {return time_offset;} private int time_offset;
	public byte[] Article_path() {return article_path;} private byte[] article_path;
	public byte[] Script_path() {return script_path;} private byte[] script_path;
	public byte[] Script() {return script;} private byte[] script;
	public byte[] Variant_article_path() {return variant_article_path;} private byte[] variant_article_path;
	public byte[] Server() {return server;} private byte[] server;
	public byte[] Server_name() {return server_name;} private byte[] server_name;
	public byte[] Wiki_id() {return wiki_id;} private byte[] wiki_id;
	public byte[] Time() {return time;} private byte[] time;
	public boolean Miser_mode() {return miser_mode;} private boolean miser_mode;
	public long Max_upload_size() {return max_upload_size;} private long max_upload_size;
	public int[] Thumb_limits() {return thumb_limits;} private int[] thumb_limits;
	public int[] Image_limits() {return image_limits;} private int[] image_limits;
	public byte[] Favicon() {return favicon;} private byte[] favicon;
	public String To_str() {return "";}
}
