


/**
 * 获取签名
 * @param noncestr
 * @param timestame
 * @param url
 * @returns {*}
 */
function getSignature(noncestr, timestame, url2) {
    var result;
    $.ajax({
        url: postHost+ '/weixin/getSignature',
        async: false,
        data: {
            NONCESTR: noncestr,
            TIMESTAME: timestame,
            URL: url2
        },
        success: function (data) {
        	data=$.parseJSON(data);console.log(data);
            if (data.msg == "success") {
            	result = data;
            } else {
                alert(data.msg);
            }
        },
        error: function () {
            alert("系统出错，请重试！");
        }
    });

    return result;
}

function wxConfBulder(timestamp,nonceStr,signature,appid) {
    wx.config({
        debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
        appId: appid, // 必填，公众号的唯一标识
        timestamp: timestamp, // 必填，生成签名的时间戳
        nonceStr: nonceStr, // 必填，生成签名的随机串
        signature: signature,// 必填，签名，见附录1
        jsApiList: [
            'onMenuShareTimeline',
            'onMenuShareAppMessage',
            'onMenuShareQQ',
            'onMenuShareWeibo',
            'onMenuShareQZone',
            'startRecord',
            'stopRecord',
            'onVoiceRecordEnd',
            'playVoice',
            'pauseVoice',
            'stopVoice',
            'onVoicePlayEnd',
            'uploadVoice',
            'downloadVoice',
            'chooseImage',
            'previewImage',
            'uploadImage',
            'downloadImage',
            'translateVoice',
            'getNetworkType',
            'openLocation',
            'getLocation',
            'hideOptionMenu',
            'showOptionMenu',
            'hideMenuItems',
            'showMenuItems',
            'hideAllNonBaseMenuItem',
            'showAllNonBaseMenuItem',
            'closeWindow',
            'scanQRCode',
            'chooseWXPay',
            'openProductSpecificView',
            'addCard',
            'chooseCard',
            'openCard']
    });
}

function wxConf(nonceStr, url, opt) {
    var timestamp = Math.round(new Date().getTime() / 1000); 
    var result = getSignature(nonceStr, timestamp, url);
    wxConfBulder(timestamp,nonceStr,result.signature,result.appid);
    var title = opt.title;
    var desc = opt.desc;
    var link = opt.link;
    var imgUrl = opt.imgUrl;
    wx.ready(function () {
        wx.onMenuShareTimeline({//分享朋友圈
            title: title,
            link: link,
            imgUrl: imgUrl,
            success: function () {
                // 用户确认分享后执行的回调函数
                collarTask(1);
            },
            cancel: function () {
                // 用户取消分享后执行的回调函数
            },
            trigger: function (res) {
            },
            fail:function (res) {
            }
        });
        wx.onMenuShareAppMessage({//分享微信好友
            title: title,
            desc: desc,
            link: link,
            imgUrl: imgUrl,
            success: function () { 
                // 用户确认分享后执行的回调函数
                collarTask(2);
            },
            cancel: function () { 
                // 用户取消分享后执行的回调函数
            },
            trigger: function (res) {
            },
           fail:function (res) {
           }
        });
        wx.onMenuShareQQ({
            title: title,
            desc: desc,
            link: link,
            imgUrl: imgUrl,
            success: function () {
                // 用户确认分享后执行的回调函数
                collarTask();
            },
            cancel: function () {
                // 用户取消分享后执行的回调函数
            },
            trigger: function (res) {
            },
            fail:function (res) {
            }
        });
        wx.onMenuShareWeibo({
            title: title,
            desc: desc,
            link: link,
            imgUrl: imgUrl,
            success: function () {
                // 用户确认分享后执行的回调函数
                collarTask();
            },
            cancel: function () {
                // 用户取消分享后执行的回调函数
            },
            trigger: function (res) {
            },
            fail:function (res) {
            }
        });
        wx.onMenuShareQZone({
            title: title,
            desc: desc,
            link: link,
            imgUrl: imgUrl,
            success: function () {
                // 用户确认分享后执行的回调函数
                collarTask();
            },
            cancel: function () {
                // 用户取消分享后执行的回调函数
            },
            trigger: function (res) {
            },
            fail:function (res) {
            }
        });

    });
}



