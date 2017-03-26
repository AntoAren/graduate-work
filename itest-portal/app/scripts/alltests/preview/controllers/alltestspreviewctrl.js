'use strict';

angular.module('itest.portal.all.preview.controllers')

    .controller('AllTestsPreviewCtrl', function($scope, $stateParams, testService, $state) {
        var loadTestPreviewInfo = function () {
            testService.getTestPreviewInfo($stateParams.testId).then(function (response) {
               $scope.test = response;
               $scope.loadingTestData = false;
            }, function () {

            });
        };

        var getBackUrl = function() {
            return $state.href('alltests', {});
        };

        $scope.backUrl = getBackUrl();
        $scope.loadingTestData = true;
        loadTestPreviewInfo();
    });
