(function() {
    'use strict';

    angular
        .module('cumminsApp')
        .controller('ProyectoConcursoDetailController', ProyectoConcursoDetailController);

    ProyectoConcursoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'ProyectoConcurso', 'Proyecto'];

    function ProyectoConcursoDetailController($scope, $rootScope, $stateParams, entity, ProyectoConcurso, Proyecto) {
        var vm = this;

        vm.proyectoConcurso = entity;

        var unsubscribe = $rootScope.$on('cumminsApp:proyectoConcursoUpdate', function(event, result) {
            vm.proyectoConcurso = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
