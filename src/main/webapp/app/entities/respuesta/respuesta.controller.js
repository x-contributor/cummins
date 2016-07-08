(function() {
    'use strict';

    angular
        .module('cumminsApp')
        .controller('RespuestaController', RespuestaController);

    RespuestaController.$inject = ['$scope', '$state', 'Respuesta'];

    function RespuestaController ($scope, $state, Respuesta) {
        var vm = this;
        
        vm.respuestas = [];

        loadAll();

        function loadAll() {
            Respuesta.query(function(result) {
                vm.respuestas = result;
            });
        }
    }
})();
