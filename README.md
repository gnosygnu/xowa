## xowa
### RELEASE NOTES
v1.6.5.1 features the following:
* Several minor parser changes / enhancements for English Wikipedia
* Minor changes for XOWA Popups (currently optional; will be default for v1.7.1). See [[Help:Options/Popups]] and [[Help:Popups]].
* Offline images for Spanish (update) and Romanian (new) wiki
See the CHANGE LOG below for a complete list of items specific to this release.

### CONTACT
XOWA's website is at http://xowa.sourceforge.net/
If you encounter issues, please post to https://sourceforge.net/p/xowa/discussion/

### CHANGE LOG
#### add:
* Module.Popups: Add wiki domain when hovering over interwiki pages; EX:en.w:Earth -> hover over link to en.q:Earth -> show "Earth wiki:en.wikiquote.org" {suggested by Schnark}. See: https://en.wikipedia.org/wiki/Earth
* Module.Popups: Add option to skip evaluation of long templates; EX:See "Template token max". See: [[Help:Options/Popups]]
* Module.Popups: Add option to show popups in namespaces only; EX:See "Allowed namespaces" {suggested by Schnark}. See: [[Help:Options/Popups]]
* Module.Popups: Add option to scan forward additional words to stop at header; EX:See "Scan forward for header" {suggested by Schnark}. See: [[Help:Options/Popups]]

#### package:
* Package: Release update for Spanish wikis.
* Package: Release initial bundle for Romanian wikis.

#### significant:
* References: Change #tag to handle recursive &lt;ref> tags (affects: refs not showing in footnotes); EX:{{#tag:ref|&lt;ref>nested&lt;/ref>}}. See: https://en.wikipedia.org/wiki/Napoleon https://en.wikipedia.org/wiki/Battle_of_Largs https://en.wikipedia.org/wiki/Battle_of_Midway https://fa.wikipedia.org/wiki/?????_???????_????? https://id.wikipedia.org/wiki/London
* Scribunto: Reset parent_frame after every call (affects: wide sidebar). See: https://en.wikipedia.org/wiki/Constantine_the_Great
* Parser.Xml: Try to extract attribute if a dangling quote (affects: compass not centered in geographical pages); EX:"key1='val1 key2='val2'" -> key2='val2' x> "". See: https://en.wikipedia.org/wiki/Aubervilliers

#### minor:
* Module.Popups: Escape \ for "show more" / "show all" (affects: math with ampersand characters do not show) {detected by Schnark}. See: https://en.wikipedia.org/wiki/Electromagnetic_tensor
* Module.Popups: Fix "Read more" not working on interwiki pages; EX:en.w:Earth -> hover over link to en.q:Earth -> click "Read more". See: https://en.wikipedia.org/wiki/Earth
* Module.Popups: Show title for hovered link in progress bar; EX:hover over link -> check progress bar (should not be blank) {detected by Schnark}.
* Module.Popups: Add sharper border to popup {suggested by Schnark}. See: /xowa/bin/any/xowa/html/modules/xowa.popups/xowa.popups.css
* Module.Popups: Do not automatically move popup to top of page when "Read more" is clicked {suggested by Schnark}.
* Module.Popups: Preserve window width / height after "Show all"; EX:Hover over link -> Click "Show all" -> Window resized -> Hover off link -> Hover back over link -> Window has same size as before.
* Module.Popups: Do not show popup when opening new page in current tab; EX:Hover over link -> Quickly click link -> No popup should show {detected by Schnark}.
* Module.Popups: Do not show popup for sidebar / portal links; EX:Hover over any link in sidebar -> No popup should show {detected by Schnark}.
* Parser.Includeonly: Handle inline &lt;noinclude> with spaces (affects: certain pages displaying incorrect pre); EX:"&lt;noinclude />". See: https://en.wikipedia.org/wiki/Wikipedia:Featured_picture_candidates
* Parser.Pre: Do not ignore new lines within wiki-style &lt;pre> (affects: new lines not showing within wiki-style pre); EX:"\n\sa\n\s\n\sb". See: https://en.wikipedia.org/wiki/Preferred_number
* Parser.Pre: Do not convert tab to space when embedded in wiki-style &lt;pre> (affects: misaligned text inside pre); EX:"\s\t" -> "&lt;pre>\t&lt;/pre>" x> "&lt;pre> &lt;/pre>". See: https://en.wikipedia.org/wiki/Cascading_Style_Sheets
* Parser.Xml: Do not cancel pre if &lt;style>, &lt;script> or &lt;form> (affects: &lt;style> showing outside pre); EX:"\s&lt;style>" -> "&lt;pre>&lt;style>&lt;/pre>" x> "\s&lt;style>". See: https://en.wikipedia.org/wiki/Cascading_Style_Sheets
* Parser.Xml: Trim space between text and &lt;/source> / &lt;/syntaxhighlight> (affects: extra pre sections); EX:"&lt;source>a  &lt;/source>" -> "&lt;source>a&lt;/source>". See: https://en.wikipedia.org/wiki/Comment_(computer_programming)
* Parser.Xml: Cancel preceding pre if &lt;/source> / &lt;/syntaxhighlight> (affects: extra pre sections); EX:" &lt;source>a&lt;/source>" -> "&lt;source>a&lt;/source>" x> "&lt;pre>&lt;source>a&lt;/source>". See: https://en.wikipedia.org/wiki/Comment_(computer_programming)
* Parser.List: Do not disable wiki-style list item if "&lt;li>" encountered (affects: missing list items); EX:"*a&lt;li>" x> "". See: https://en.wikipedia.org/wiki/Bristol_Bullfinch
* Parser.List: Close wiki-style lists with corresponding xml nodes (affects: text not showing up on different lines); EX:"*a&lt;/ul". See: https://en.wikipedia.org/wiki/Bristol_Bullfinch
* Parser.List: Allow "&lt;li>" to be be nestable (affects: recent news box in rowiki); EX:&lt;li>&lt;span>a&lt;li>&lt;span>b. See: https://ro.wikipedia.org/wiki/Pagina_principala
* Parser.List: Remove XOWA tidy logic to always assert &lt;ul> (affects: recent news box in rowiki); EX:"&lt;li>" x> "&lt;ul>&lt;li>". See: https://ro.wikipedia.org/wiki/Pagina_principala
* Parser.List: Remove XOWA tidy logic to remove duplicate &lt;ul> (affects: recent news box in rowiki); EX:"&lt;li>&lt;li>" -> "&lt;li>&lt;li>" x> "&lt;li>". See: https://ro.wikipedia.org/wiki/Pagina_principala
* Parser.Xml: Do not pair end tag with start tag that is before table (affects: stray "|}" showing on page); EX:"&lt;div>\n{|&lt;/div>" -> &lt;/div> should not close &lt;div>. See: https://ro.wikibooks.org/wiki/Pagina_principala
* Parser.Table: Do not fix &lt;caption>&lt;table> sequence (affects: unclosed caption to put rest of page in table). See: https://es.wikipedia.org/wiki/Sevilla
* Parser.Table: Do not treat !! as header separator if current token is link (affects: links with !! showing as text); EX:[[A!!B]]. See: https://en.wikipedia.org/wiki/Pink_(singer)
* Parser.Link: Use last two characters of "]]]" to close link, not first (affects: stray ] showing on a handful of pages); EX:"[[A|[b]]]". See: https://es.wiktionary.org/wiki/casa https://es.wiktionary.org/wiki/sol https://ru.wikipedia.org/wiki/?????????_??_????_??_????
* Parser.Xml: Close open blockquote if new blockquote encountered (affects: indenting paragraphs due to malformed html). See: https://en.wikipedia.org/wiki/Ring_a_Ring_o'_Roses
* Parser.Time: Infer day if flanked by month and year (affects: one page in enwiki not showing time values); EX:"12:34 May 6 2010. See: https://en.wikipedia.org/wiki/Wikipedia:WikiProject_Maine/members
* References: Skip nested &lt;references/> (affects: missing references on a handful of pages); EX:"&lt;references>&lt;references/>&lt;/references>". See: https://en.wikipedia.org/wiki/Hwair

#### dev:
* Log: Do not hard-code "xowa.jar" as jar name (affects: xowa.jar showing in logs). See: [[Special:XowaSystemData?type=log_session]]
* File.Offline.v2: Fix wmf always being 1st download source (over sql) (affects: v1.6.3.1).
