(function() {
    'use strict';

    angular
        .module('cumminsApp')
        .controller('ProyectoController', ProyectoController);

    ProyectoController.$inject = ['$scope', '$state', 'Proyecto'];

    function ProyectoController ($scope, $state, Proyecto) {
        var vm = this;
        
        vm.proyectos = [];

        loadAll();

        function loadAll() {
            Proyecto.query(function(result) {
                vm.proyectos = result;
            });
        }
    }
})();
