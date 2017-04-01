'use strict';

angular.module('itest.portal.common.services')

.factory('orderDialog', function(ngDialog) {
    return {
        open: function(item, header, text) {
            return ngDialog.openConfirm({
                template: 'views/common/orderdialog-template.html',
                closeByEscape: true,
                showClose: false,
                data: {
                    item: item,
                    header: header,
                    text: text
                }
           });
        }
    };
});
