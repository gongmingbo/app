function Page(opt) {
    var set = $.extend({
        num: null,
        startnum: 1,
        elem: null,
        callback: null
    }, opt || {});
    if (set.startnum < 1) {
        set.startnum = 1;
    }else if(isNaN(parseInt(set.startnum))){
        console.log("您输入页码错误");
        set.startnum = parseInt(set.num);
    }else if(!isNaN(parseInt(set.startnum)) && set.startnum > parseInt(set.num)){
        set.startnum = parseInt(set.num);
    }
    var n = 0,
        htm = '';
    var clickpages = {
        elem: set.elem,
        num: parseInt(set.num),
        callback: set.callback,
        init: function() {
            this.elem.next('div.pageJump').children('.button').unbind('click')
            this.elem.children('li').click(function() {
                var txt = $(this).children('a').text();
                var page = '',
                    ele = null;
                var page1 = parseInt(clickpages.elem.children('li.active').attr('page'));
                if (isNaN(parseInt(txt))) {
                    switch (txt) {
                        case '下一页':
                            if (page1 == clickpages.num) {
                                return;
                            }
                            if (page1 >= (clickpages.num - 2) || clickpages.num <= 6 || page1 < 3) {
                                ele = clickpages.elem.children('li.active').next();
                            } else {
                                clickpages.newPages('next', parseInt(page1) + 1);
                                ele = clickpages.elem.children('li.active');
                            }
                            break;
                        case '上一页':
                            if (page1 == '1') {
                                return;
                            }
                            if (page1 >= (clickpages.num - 1) || page1 <= 3 || clickpages.num <= 6) {
                                ele = clickpages.elem.children('li.active').prev();
                            } else {
                                clickpages.newPages('prev', parseInt(page1) - 1);
                                ele = clickpages.elem.children('li.active');
                            }
                            break;
                        case '首页':
                            if (page1 == '1') {
                                return;
                            }
                            if (clickpages.num > 6) {
                                clickpages.newPages('首页', 1);
                            }
                            ele = clickpages.elem.children('li[page=1]');
                            break;
                        case '尾页':
                            if (page1 == clickpages.num) {
                                return;
                            }
                            if (clickpages.num > 6) {
                                clickpages.newPages('尾页', clickpages.num);
                            }
                            ele = clickpages.elem.children('li[page=' + clickpages.num + ']');
                            break;
                        case '...':
                            return;
                    }
                } else {
                    if ((parseInt(txt) >= (clickpages.num - 3) || parseInt(txt) <= 3) && clickpages.num > 6) {
                        clickpages.newPages('jump', parseInt(txt));
                    }
                    ele = $(this);
                }
                page = clickpages.actPages(ele);
                if (page != '' && page != page1) {
                    if (clickpages.callback) {
                        clickpages.callback(parseInt(page));
                    }
                }
            });
        },
        //active
        actPages: function(ele) {
            ele.addClass('active').siblings().removeClass('active');
            return clickpages.elem.children('li.active').text();
        },

        //newpages
        newPages: function(type, i) {
            if(isNaN(parseInt(i))){
                alert("输入页码错误");
                return;
            }
            var html = "",
                htmlLeft = "",
                htmlRight = "",
                htmlC = "";
            var HL = '<li><a>...</a></li>';
            html = '<li><a  aria-label="Previous">首页</a></li>\
                    <li><a class="page_prev">上一页</a></li>'
            for (var n = 0; n < 3; n++) {
                htmlC += '<li ' + ((n - 1) == 0 ? 'class="active"' : '') + ' page="' + (i + n - 1) + '"><a>' + (i + n - 1) + '</a></li>';
                htmlLeft += '<li ' + ((n + 2) == i ? 'class="active"' : '') + ' page="' + (n + 2) + '"><a>' + (n + 2) + '</a></li>';
                htmlRight += '<li ' + ((parseInt(set.num) + n - 3) == i ? 'class="active"' : '') + ' page="' + (parseInt(set.num) + n - 3) + '"><a>' + (parseInt(set.num) + n - 3) + '</a></li>';
            }

            switch (type) {
                case "next":
                    if (i <= 4) {
                        html += '<li page="1"><a>1</a></li>' + htmlLeft + HL + '<li page="' + parseInt(set.num) + '"><a>' + parseInt(set.num) + '</a></li>';
                    } else if (i >= (parseInt(set.num) - 3)) {
                        html += '<li page="1"><a>1</a></li>' + HL + htmlRight + '<li page="' + parseInt(set.num) + '"><a>' + parseInt(set.num) + '</a></li>';
                    } else {
                        html += '<li page="1"><a>1</a></li>' + HL + htmlC + HL + '<li page="' + parseInt(set.num) + '"><a>' + parseInt(set.num) + '</a></li>';
                    }
                    break;
                case "prev":
                    if (i <= 4) {
                        html += '<li page="1"><a>1</a></li>' + htmlLeft + HL + '<li page="' + parseInt(set.num) + '"><a>' + parseInt(set.num) + '</a></li>';
                    } else if (i >= (parseInt(set.num) - 3)) {
                        html += '<li page="1"><a>1</a></li>' + HL + htmlRight + '<li page="' + parseInt(set.num) + '"><a>' + parseInt(set.num) + '</a></li>';
                    } else {
                        html += '<li page="1"><a>1</a></li>' + HL + htmlC + HL + '<li page="' + parseInt(set.num) + '"><a>' + parseInt(set.num) + '</a></li>';
                    }
                    break;
                case "首页":
                    html += '<li class="active" page="1"><a>1</a></li>' + htmlLeft + HL + '<li page="' + parseInt(set.num) + '"><a>' + parseInt(set.num) + '</a></li>';
                    break;
                case "尾页":
                    html += '<li page="1"><a>1</a></li>' + HL + htmlRight + '<li class="active" page="' + parseInt(set.num) + '"><a>' + parseInt(set.num) + '</a></li>';
                    break;
                case "jump":
                    if (i <= 4) {
                        if (i == 1) {
                            html += '<li class="active" page="1"><a>1</a></li>' + htmlLeft + HL + '<li page="' + parseInt(set.num) + '"><a>' + parseInt(set.num) + '</a></li>';
                        } else {
                            html += '<li page="1"><a>1</a></li>' + htmlLeft + HL + '<li page="' + parseInt(set.num) + '"><a>' + parseInt(set.num) + '</a></li>';
                        }
                    } else if ((i >= parseInt(set.num) - 3) && (parseInt(set.num) >= 7)) {
                        if (i == parseInt(set.num)) {
                            html += '<li page="1"><a>1</a></li>' + HL + htmlRight + '<li class="active" page="' + parseInt(set.num) + '"><a>' + parseInt(set.num) + '</a></li>';
                        } else {
                            html += '<li page="1"><a>1</a></li>' + HL + htmlRight + '<li page="' + parseInt(set.num) + '"><a>' + parseInt(set.num) + '</a></li>';
                        }
                    } else {
                        html += '<li page="1"><a>1</a></li>' + HL + htmlC + HL + '<li page="' + parseInt(set.num) + '"><a>' + parseInt(set.num) + '</a></li>';
                    }
            }
            html += '<li><a class="page_next">下一页</a></li>\
                    <li><a  aria-label="Next">尾页</a></li>';
            if (this.num > 5 || this.num < 3) {
                set.elem.html(html);
                clickpages.init({
                    num: parseInt(set.num),
                    elem: set.elem,
                    callback: set.callback
                });
            }
        }
    }
    if (parseInt(set.num) < 1) {
        $(".pagination").html('');
        return;
    } else if (parseInt(set.num) <= 6) {
        n = parseInt(set.num);
        var html = '<li><a  aria-label="Previous">首页</a></li>\
                    <li><a class="page_prev">上一页</a></li>';
        for (var i = 1; i <= n; i++) {
            if (i == set.startnum) {
                html += '<li class="active" page="' + i + '"><a>' + i + '</a></li>';
            } else {
                html += '<li page="' + i + '"><a>' + i + '</a></li>';
            }
        }
        html += '<li><a class="page_next">下一页</a></li>\
                    <li><a  aria-label="Next">尾页</a></li>';
        set.elem.html(html);
        clickpages.init();
    } else {
        clickpages.newPages("jump", parseInt(set.startnum))
    }
}