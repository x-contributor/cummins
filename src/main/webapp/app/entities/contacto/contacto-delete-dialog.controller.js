(function() {
    'use strict';

    angular
        .module('cumminsApp')
        .controller('ContactoDeleteController',ContactoDeleteController);

    ContactoDeleteController.$inject = ['$uibModalInstance', 'entity', 'Contacto'];

    function ContactoDeleteController($uibModalInstance, entity, Contacto) {
        var vm = this;

        vm.contacto = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Contacto.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
