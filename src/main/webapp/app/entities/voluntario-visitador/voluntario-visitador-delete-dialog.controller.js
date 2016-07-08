(function() {
    'use strict';

    angular
        .module('cumminsApp')
        .controller('VoluntarioVisitadorDeleteController',VoluntarioVisitadorDeleteController);

    VoluntarioVisitadorDeleteController.$inject = ['$uibModalInstance', 'entity', 'VoluntarioVisitador'];

    function VoluntarioVisitadorDeleteController($uibModalInstance, entity, VoluntarioVisitador) {
        var vm = this;

        vm.voluntarioVisitador = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            VoluntarioVisitador.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
