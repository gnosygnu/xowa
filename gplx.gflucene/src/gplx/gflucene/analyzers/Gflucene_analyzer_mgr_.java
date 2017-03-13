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
package gplx.gflucene.analyzers; import gplx.*; import gplx.gflucene.*;
import gplx.gflucene.core.*;
import org.apache.lucene.analysis.Analyzer;
public class Gflucene_analyzer_mgr_ {
		public static Analyzer New_analyzer(String key) {
		if 		(String_.Eq(key, "standard"))        return new org.apache.lucene.analysis.standard.StandardAnalyzer();
		else if (String_.Eq(key, "ar"))              return new org.apache.lucene.analysis.ar.ArabicAnalyzer();
		else if (String_.Eq(key, "bg"))              return new org.apache.lucene.analysis.bg.BulgarianAnalyzer();
//		else if (String_.Eq(key, "br"))              return new org.apache.lucene.analysis.br.BrazilianAnalyzer();
		else if (String_.Eq(key, "ca"))              return new org.apache.lucene.analysis.ca.CatalanAnalyzer();
		else if (String_.Eq(key, "cjk"))             return new org.apache.lucene.analysis.cjk.CJKAnalyzer();
		else if (String_.Eq(key, "ckb"))             return new org.apache.lucene.analysis.ckb.SoraniAnalyzer();
		else if (String_.Eq(key, "cz"))              return new org.apache.lucene.analysis.cz.CzechAnalyzer();
		else if (String_.Eq(key, "da"))              return new org.apache.lucene.analysis.da.DanishAnalyzer();
		else if (String_.Eq(key, "de"))              return new org.apache.lucene.analysis.de.GermanAnalyzer();
		else if (String_.Eq(key, "el"))              return new org.apache.lucene.analysis.el.GreekAnalyzer();
		else if (String_.Eq(key, "en"))              return new org.apache.lucene.analysis.en.EnglishAnalyzer();
		else if (String_.Eq(key, "es"))              return new org.apache.lucene.analysis.es.SpanishAnalyzer();
		else if (String_.Eq(key, "eu"))              return new org.apache.lucene.analysis.eu.BasqueAnalyzer();
		else if (String_.Eq(key, "fa"))              return new org.apache.lucene.analysis.fa.PersianAnalyzer();
		else if (String_.Eq(key, "fi"))              return new org.apache.lucene.analysis.fi.FinnishAnalyzer();
		else if (String_.Eq(key, "fr"))              return new org.apache.lucene.analysis.fr.FrenchAnalyzer();
		else if (String_.Eq(key, "ga"))              return new org.apache.lucene.analysis.ga.IrishAnalyzer();
		else if (String_.Eq(key, "gl"))              return new org.apache.lucene.analysis.gl.GalicianAnalyzer();
		else if (String_.Eq(key, "hi"))              return new org.apache.lucene.analysis.hi.HindiAnalyzer();
		else if (String_.Eq(key, "hu"))              return new org.apache.lucene.analysis.hu.HungarianAnalyzer();
		else if (String_.Eq(key, "hy"))              return new org.apache.lucene.analysis.hy.ArmenianAnalyzer();
		else if (String_.Eq(key, "id"))              return new org.apache.lucene.analysis.id.IndonesianAnalyzer();
		else if (String_.Eq(key, "it"))              return new org.apache.lucene.analysis.it.ItalianAnalyzer();
		else if (String_.Eq(key, "lt"))              return new org.apache.lucene.analysis.lt.LithuanianAnalyzer();
		else if (String_.Eq(key, "lv"))              return new org.apache.lucene.analysis.lv.LatvianAnalyzer();
		else if (String_.Eq(key, "nl"))              return new org.apache.lucene.analysis.nl.DutchAnalyzer();
		else if (String_.Eq(key, "no"))              return new org.apache.lucene.analysis.no.NorwegianAnalyzer();
		else if (String_.Eq(key, "pt"))              return new org.apache.lucene.analysis.pt.PortugueseAnalyzer();
		else if (String_.Eq(key, "ro"))              return new org.apache.lucene.analysis.ro.RomanianAnalyzer();
		else if (String_.Eq(key, "ru"))              return new org.apache.lucene.analysis.ru.RussianAnalyzer();
		else if (String_.Eq(key, "sv"))              return new org.apache.lucene.analysis.sv.SwedishAnalyzer();
		else if (String_.Eq(key, "th"))              return new org.apache.lucene.analysis.th.ThaiAnalyzer();
		else if (String_.Eq(key, "tr"))              return new org.apache.lucene.analysis.tr.TurkishAnalyzer();
		else                                         throw Err_.new_unhandled_default(key);
	}
	}
