== Sample files ==

In /xowa/bin/any/xowa/cfg/wiki/api/, each folder has a sample file that can be used for a wiki. To use it do the following:

* Copy the "-sample.csv" file
* Replace the "-sample" to the name of the wiki. For example, for nethackwiki.com, rename the file to "extensiontags-nethackwiki.com.csv". For English Wikipedia, use "extensiontags-en.wikipedia.org.csv"
* Import the wiki. XOWA will use the data in the file when creating the wiki

This data gets stored in the main ".xowa" database in the xowa_cfg table. For example, run the following SQL: SELECT * FROM xowa_cfg WHERE cfg_key = 'extensiontags';

The remainder of this article documents the purpose of each file.

== extensiontags ==

The "extensiontags" file lists active extensions. For example, to enable only &lt;ref> and &lt;references, use the following two lines:

<pre>
ref
references
</pre>

All other extension tags will be disabled. For example, &lt;math> will show up as just &lt;math> LaTeX source instead of being replaced with graphical equations

The main purpose of this functionality is to disable extensions in some wikis. For example, the <do> extension is only active in the Wikivoyage wiki. Otherwise, English Wikipedia has an article called [https://en.wikipedia.org/wiki/Type_system Type system] which will render incorrectly with the following fragment:

<pre>
<code>if <complex test> then <do something> else <generate type error></code>
</pre>

== interwikimap ==
The "interwikimap" file lists active interwiki prefixes. For example, to enable only the interwiki prefix for commons, use the following:

<pre>
commons|https://commons.wikimedia.org/wiki/~{0}
</pre>

This will convert &91;&91;commons:Earth]] to a link to https://commons.wikipedia.org/wiki/Earth