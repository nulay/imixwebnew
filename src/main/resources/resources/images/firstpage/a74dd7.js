(function (d) {
    var f = function (d) {
        var n1 = document.getElementsByTagName("link")[0], s1 = document.createElement("link"), r1 = 1 * new Date();
        s1.type = "text/css";
        s1.rel = "stylesheet";
        s1.href = "https://cdn.bitrix24.ru/b1575061/livechat/script/livechat.css?r=1508315574";
        var n2 = document.getElementsByTagName("script")[0], s2 = document.createElement("script");
        s2.type = "text/javascript";
        s2.async = "true";
        s2.charset = "UTF-8";
        s2.src = "https://cdn.bitrix24.ru/b1575061/livechat/script/livechat.js?r=1508315574";
        if (n1) {
            n1.parentNode.insertBefore(s1, n1);
        } else {
            n2.parentNode.insertBefore(s1, n2);
        }
        n2.parentNode.insertBefore(s2, n2);
    };
    if (typeof (BX) != "undefined" && typeof (BX.ready) != "undefined") {
        BX.ready(f)
    } else if (typeof (jQuery) != "undefined") {
        jQuery(f)
    } else {
        setTimeout(f, 150);
    }
})(document);
(window.BxLiveChatLoader = window.BxLiveChatLoader || []).push(function () {
    BX.LiveChatMessage.add({
        'DEFAULT_TITLE': 'Открытая линия',
        'READY_TO_RESPOND': 'Наши специалисты готовы помочь вам',
        'RESPOND_LATER': 'Наши специалисты ответят вам в самое ближайшее время',
        'LOADING_MESSAGE': 'Напишите сообщение и мы ответим вам в самое ближайшее время',
        'SONET_ICONS': 'Хотите общаться с нами удобным для вас способом?',
        'SONET_ICONS_CLICK': 'кликните по нужной иконке и напишите нам через удобное для вас приложение',
        'TEXTAREA_HOTKEY': 'Кликните для смены горячей клавиши',
        'TEXTAREA_PLACEHOLDER': 'Введите сообщение',
        'TEXTAREA_SEND': 'Отправить сообщение',
        'TEXTAREA_FILE': 'Отправить файл',
        'TEXTAREA_SMILE': 'Выбор смайлов',
        'POWERED_BY': 'Заряжено',
        'ERROR_TITLE': 'К сожалению, загрузить онлайн-чат не удалось :(',
        'ERROR_3RD_PARTY_COOKIE_DESC': 'Отключите опцию \"Блокировать данные и файлы cookie сторонних сайтов\" в настройках вашего браузера или воспользуйтесь другими каналами связи, представленными ниже:',
        'ERROR_INTRANET_USER_DESC': 'Вы не можете написать в онлайн-чат, так как в этом браузере вы уже авторизованы как сотрудник этого Битрикс24. Откройте онлайн-чат в другом браузере или выйдите со своего аккаунта Битрикс24 по адресу #URL#',
        'ERROR_INTRANET_USER_DESC_2': 'Вы не можете написать в онлайн-чат, так как в этом браузере вы уже авторизованы как сотрудник этого Битрикс24. Но вы можете написать в эту открытую линию у себя на #URL_START#портале#URL_END# или воспользоваться другими каналами связи, представленными ниже:',
        'ERROR_UNKNOWN': 'Пожалуйста, воспользуйтесь другими каналами связи'
    });
    BX.LiveChat.init({
        "copyright": true,
        "bitrix24": "https:\/\/wdcompany.bitrix24.ru",
        "code": "a74dd7",
        "lang": null,
        "copyrightUrl": "https:\/\/www.bitrix24.ru\/?p=1338541",
        "button": false
    });
});