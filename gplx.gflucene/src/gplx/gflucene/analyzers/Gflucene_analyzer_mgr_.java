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
package gplx.gflucene.analyzers;
import gplx.types.basics.utls.StringUtl;
import gplx.types.errs.ErrUtl;
import org.apache.lucene.analysis.Analyzer;
public class Gflucene_analyzer_mgr_ {
		public static Analyzer New_analyzer(String key) {
		if 		(StringUtl.Eq(key, "standard"))        return new org.apache.lucene.analysis.standard.StandardAnalyzer();
		else if (StringUtl.Eq(key, "ar"))              return new org.apache.lucene.analysis.ar.ArabicAnalyzer();
		else if (StringUtl.Eq(key, "bg"))              return new org.apache.lucene.analysis.bg.BulgarianAnalyzer();
//		else if (String_.Eq(key, "br"))              return new org.apache.lucene.analysis.br.BrazilianAnalyzer();
		else if (StringUtl.Eq(key, "ca"))              return new org.apache.lucene.analysis.ca.CatalanAnalyzer();
		else if (StringUtl.Eq(key, "cjk"))             return new org.apache.lucene.analysis.cjk.CJKAnalyzer();
		else if (StringUtl.Eq(key, "ckb"))             return new org.apache.lucene.analysis.ckb.SoraniAnalyzer();
		else if (StringUtl.Eq(key, "cz"))              return new org.apache.lucene.analysis.cz.CzechAnalyzer();
		else if (StringUtl.Eq(key, "da"))              return new org.apache.lucene.analysis.da.DanishAnalyzer();
		else if (StringUtl.Eq(key, "de"))              return new org.apache.lucene.analysis.de.GermanAnalyzer();
		else if (StringUtl.Eq(key, "el"))              return new org.apache.lucene.analysis.el.GreekAnalyzer();
		else if (StringUtl.Eq(key, "en"))              return new org.apache.lucene.analysis.en.EnglishAnalyzer();
		else if (StringUtl.Eq(key, "es"))              return new org.apache.lucene.analysis.es.SpanishAnalyzer();
		else if (StringUtl.Eq(key, "eu"))              return new org.apache.lucene.analysis.eu.BasqueAnalyzer();
		else if (StringUtl.Eq(key, "fa"))              return new org.apache.lucene.analysis.fa.PersianAnalyzer();
		else if (StringUtl.Eq(key, "fi"))              return new org.apache.lucene.analysis.fi.FinnishAnalyzer();
		else if (StringUtl.Eq(key, "fr"))              return new org.apache.lucene.analysis.fr.FrenchAnalyzer();
		else if (StringUtl.Eq(key, "ga"))              return new org.apache.lucene.analysis.ga.IrishAnalyzer();
		else if (StringUtl.Eq(key, "gl"))              return new org.apache.lucene.analysis.gl.GalicianAnalyzer();
		else if (StringUtl.Eq(key, "hi"))              return new org.apache.lucene.analysis.hi.HindiAnalyzer();
		else if (StringUtl.Eq(key, "hu"))              return new org.apache.lucene.analysis.hu.HungarianAnalyzer();
		else if (StringUtl.Eq(key, "hy"))              return new org.apache.lucene.analysis.hy.ArmenianAnalyzer();
		else if (StringUtl.Eq(key, "id"))              return new org.apache.lucene.analysis.id.IndonesianAnalyzer();
		else if (StringUtl.Eq(key, "it"))              return new org.apache.lucene.analysis.it.ItalianAnalyzer();
		else if (StringUtl.Eq(key, "lt"))              return new org.apache.lucene.analysis.lt.LithuanianAnalyzer();
		else if (StringUtl.Eq(key, "lv"))              return new org.apache.lucene.analysis.lv.LatvianAnalyzer();
		else if (StringUtl.Eq(key, "nl"))              return new org.apache.lucene.analysis.nl.DutchAnalyzer();
		else if (StringUtl.Eq(key, "no"))              return new org.apache.lucene.analysis.no.NorwegianAnalyzer();
		else if (StringUtl.Eq(key, "pt"))              return new org.apache.lucene.analysis.pt.PortugueseAnalyzer();
		else if (StringUtl.Eq(key, "ro"))              return new org.apache.lucene.analysis.ro.RomanianAnalyzer();
		else if (StringUtl.Eq(key, "ru"))              return new org.apache.lucene.analysis.ru.RussianAnalyzer();
		else if (StringUtl.Eq(key, "sv"))              return new org.apache.lucene.analysis.sv.SwedishAnalyzer();
		else if (StringUtl.Eq(key, "th"))              return new org.apache.lucene.analysis.th.ThaiAnalyzer();
		else if (StringUtl.Eq(key, "tr"))              return new org.apache.lucene.analysis.tr.TurkishAnalyzer();
		else                                         throw ErrUtl.NewUnhandled(key);
	}
	}
