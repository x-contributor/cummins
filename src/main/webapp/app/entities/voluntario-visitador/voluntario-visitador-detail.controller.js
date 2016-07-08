(function() {
    'use strict';

    angular
        .module('cumminsApp')
        .controller('VoluntarioVisitadorDetailController', VoluntarioVisitadorDetailController);

    VoluntarioVisitadorDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'VoluntarioVisitador', 'Proyecto'];

    function VoluntarioVisitadorDetailController($scope, $rootScope, $stateParams, entity, VoluntarioVisitador, Proyecto) {
        var vm = this;

        vm.voluntarioVisitador = entity;

        var unsubscribe = $rootScope.$on('cumminsApp:voluntarioVisitadorUpdate', function(event, result) {
            vm.voluntarioVisitador = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
