(function() {
    'use strict';

    angular
        .module('cumminsApp')
        .controller('ProyectoDetailController', ProyectoDetailController);

    ProyectoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Proyecto', 'VoluntarioVisitador', 'Participante'];

    function ProyectoDetailController($scope, $rootScope, $stateParams, entity, Proyecto, VoluntarioVisitador, Participante) {
        var vm = this;

        vm.proyecto = entity;

        var unsubscribe = $rootScope.$on('cumminsApp:proyectoUpdate', function(event, result) {
            vm.proyecto = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
