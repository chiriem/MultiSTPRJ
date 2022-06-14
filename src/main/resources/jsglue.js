
var Flask = new (function(){
    'use strict';
    return {
        '_endpoints': [["app_multilang.search_ip", ["/", "/search-ip/", ""], ["lang_code", "IP_ADDR"]], ["app_ip.search_ip", ["/search-ip/", ""], ["IP_ADDR"]], ["static", ["/static/", ""], ["filename"]], ["app_youtube_tool.youtube_multi_view_watch", ["/watch/", ""], ["videoUrls"]], ["app_multilang.youtube_comment_picker", ["/", "/youtube-comment-picker"], ["lang_code"]], ["app_multilang.youtube_first_comment", ["/", "/youtube-first-comment"], ["lang_code"]], ["app_multilang.subtitle_index", ["/", "/subtitle-converter"], ["lang_code"]], ["app_multilang.youtube_multi_view", ["/", "/youtube-multi-view"], ["lang_code"]], ["app_multilang.barcode_generator", ["/", "/barcode-generator"], ["lang_code"]], ["app_multilang.favicon_generator", ["/", "/favicon-generator"], ["lang_code"]], ["app_multilang.qrcode_generator", ["/", "/qrcode-generator"], ["lang_code"]], ["app_multilang.image_converter", ["/", "/image-converter"], ["lang_code"]], ["app_multilang.image_resize", ["/", "/image-resize"], ["lang_code"]], ["app_multilang.site_map", ["/", "/sitemap.xml"], ["lang_code"]], ["app_multilang.search_ip", ["/", "/search-ip"], ["lang_code"]], ["app_multilang.signin_page", ["/", "/signin"], ["lang_code"]], ["app_multilang.my_ip", ["/", "/my-ip"], ["lang_code"]], ["app_multilang.main_page", ["/", "/"], ["lang_code"]], ["cssfiles", ["/static/css/emweb.css"], []], ["staticfiles", ["/.well-known/brave-rewards-verification.txt"], []], ["app_youtube_tool.get_firstComment_youtube", ["/get_firstComment_youtube"], []], ["app_comment_picker.youtube_comment_picker", ["/youtube-comment-picker"], []], ["app_youtube_tool.youtube_first_comment", ["/youtube-first-comment"], []], ["app_comment_picker.pick_comment_youtube", ["/pick_comment_youtube"], []], ["app_comment_picker.get_comments_youtube", ["/get_comments_youtube"], []], ["app_subtitle.subtitle_index", ["/subtitle-converter"], []], ["app_youtube_tool.youtube_multi_view", ["/youtube-multi-view"], []], ["app_favicon_generator.favicon_generator", ["/favicon-generator"], []], ["app_comment_picker.get_video_youtube", ["/get_video_youtube"], []], ["app_barcode_generator.barcode_generator", ["/barcode-generator"], []], ["app_comment_picker.conv_tag_youtube", ["/conv_tag_youtube"], []], ["app_barcode_generator.qrcode_generator", ["/qrcode-generator"], []], ["app_image_converter.image_converter", ["/image-converter"], []], ["app_translator.translator_all", ["/translator-all"], []], ["load_analytics", ["/load_analytics"], []], ["app_image_converter.convert_image", ["/convert_image"], []], ["app_ip.get_search_ip", ["/get_search_ip"], []], ["app_translator.insert_cdata", ["/insert_cdata"], []], ["app_translator.update_cdata", ["/update_cdata"], []], ["app_translator.delete_cdata", ["/delete_cdata"], []], ["app_image_converter.image_resize", ["/image-resize"], []], ["app_image_converter.resize_image", ["/resize_image"], []], ["app_barcode_generator.make_barcode", ["/make_barcode"], []], ["app_subtitle.analysis_sub", ["/analysis_sub"], []], ["app_translator.remove_lang", ["/remove-lang"], []], ["app_barcode_generator.make_qrcode", ["/make_qrcode"], []], ["site_map", ["/sitemap.xml"], []], ["staticfiles", ["/favicon.ico"], []], ["app_translator.lan_change", ["/lan_change"], []], ["app_translator.translator", ["/translator"], []], ["app_translator.load_cdata", ["/load_cdata"], []], ["app_translator.save_cdata", ["/save_cdata"], []], ["app_comment_picker.copytoclip", ["/copytoclip"], []], ["staticfiles", ["/robots.txt"], []], ["serve_js", ["/jsglue.js"], []], ["app_ip.search_ip", ["/search-ip"], []], ["app_ip.get_my_ip", ["/get_my_ip"], []], ["signinchk", ["/signinchk"], []], ["page_edit", ["/page-edit"], []], ["app_translator.get_text", ["/get-text"], []], ["app_translator.add_lang", ["/add-lang"], []], ["app_translator.lang_set", ["/lang-set"], []], ["adsblock", ["/adsblock"], []], ["app_favicon_generator.convert", ["/convert"], []], ["staticfiles", ["/ads.txt"], []], ["signout", ["/signout"], []], ["signin_page", ["/signin"], []], ["app_ip.my_ip", ["/my-ip"], []], ["chart", ["/chart"], []], ["visits", ["/vc"], []], ["main_page", ["/"], []]],
        'url_for': function(endpoint, rule) {
            if(typeof rule === "undefined") rule = {};

            var has_everything = false, url = "";

            var is_absolute = false, has_anchor = false, has_scheme;
            var anchor = "", scheme = "";

            if(rule['_external'] === true) {
                is_absolute = true;
                scheme = location.protocol.split(':')[0];
                delete rule['_external'];
            }

            if('_scheme' in rule) {
                if(is_absolute) {
                    scheme = rule['_scheme'];
                    delete rule['_scheme'];
                } else {
                    throw {name:"ValueError", message:"_scheme is set without _external."};
                }
            }

            if('_anchor' in rule) {
                has_anchor = true;
                anchor = rule['_anchor'];
                delete rule['_anchor'];
            }

            for(var i in this._endpoints) {
                if(endpoint == this._endpoints[i][0]) {
                    var url = '';
                    var j = 0;
                    var has_everything = true;
                    var used = {};
                    for(var j = 0; j < this._endpoints[i][2].length; j++) {
                        var t = rule[this._endpoints[i][2][j]];
                        if(typeof t === "undefined") {
                            has_everything = false;
                            break;
                        }
                        url += this._endpoints[i][1][j] + t;
                        used[this._endpoints[i][2][j]] = true;
                    }
                    if(has_everything) {
                        if(this._endpoints[i][2].length != this._endpoints[i][1].length)
                            url += this._endpoints[i][1][j];

                        var first = true;
                        for(var r in rule) {
                            if(r[0] != '_' && !(r in used)) {
                                if(first) {
                                    url += '?';
                                    first = false;
                                } else {
                                    url += '&';
                                }
                                url += r + '=' + rule[r];
                            }
                        }
                        if(has_anchor) {
                            url += "#" + anchor;
                        }

                        if(is_absolute) {
                            return scheme + "://" + location.host + url;
                        } else {
                            return url;
                        }
                    }
                }
            }

            throw {name: 'BuildError', message: "Couldn't find the matching endpoint."};
        }
    };
});