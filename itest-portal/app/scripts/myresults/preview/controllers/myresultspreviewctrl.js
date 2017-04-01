'use strict';

angular.module('itest.portal.myresults.preview.controllers')

    .controller('MyResultsPreviewCtrl', function($scope, $stateParams, testService, $state) {
        var loadTestPreviewInfo = function () {
            var params = {
                view: 'myResults'
            };
            testService.getPreviewInfo($stateParams.testId, params).then(function (response) {
                $scope.test = response;
                $scope.loadingTestData = false;
            }, function () {

            });
        };

        var getBackUrl = function() {
            return $state.href('myresults', {});
        };

        $scope.backUrl = getBackUrl();
        $scope.loadingTestData = true;
        loadTestPreviewInfo();
    });
