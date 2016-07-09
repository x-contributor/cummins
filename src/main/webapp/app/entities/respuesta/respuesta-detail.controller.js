(function() {
    'use strict';

    angular
        .module('cumminsApp')
        .controller('RespuestaDetailController', RespuestaDetailController);

    RespuestaDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Respuesta', 'Proyecto'];

    function RespuestaDetailController($scope, $rootScope, $stateParams, entity, Respuesta, Proyecto) {
        var vm = this;

        vm.respuesta = entity;

        var unsubscribe = $rootScope.$on('cumminsApp:respuestaUpdate', function(event, result) {
            vm.respuesta = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
