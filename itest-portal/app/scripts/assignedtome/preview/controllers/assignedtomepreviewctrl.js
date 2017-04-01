'use strict';

angular.module('itest.portal.assignedtome.preview.controllers')

    .controller('AssignedToMePreviewCtrl', function($scope, $stateParams, testService, $state) {
        var loadTestPreviewInfo = function () {
            var params = {
                view: 'assignedToMe'
            };
            testService.getPreviewInfo($stateParams.testId, params).then(function (response) {
                $scope.test = response;
                $scope.loadingTestData = false;
            }, function () {

            });
        };

        var getBackUrl = function() {
            return $state.href('assignedtome', {});
        };

        $scope.backUrl = getBackUrl();
        $scope.loadingTestData = true;
        loadTestPreviewInfo();
    });
