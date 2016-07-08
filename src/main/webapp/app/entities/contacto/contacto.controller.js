(function() {
    'use strict';

    angular
        .module('cumminsApp')
        .controller('ContactoController', ContactoController);

    ContactoController.$inject = ['$scope', '$state', 'Contacto'];

    function ContactoController ($scope, $state, Contacto) {
        var vm = this;
        
        vm.contactos = [];

        loadAll();

        function loadAll() {
            Contacto.query(function(result) {
                vm.contactos = result;
            });
        }
    }
})();
