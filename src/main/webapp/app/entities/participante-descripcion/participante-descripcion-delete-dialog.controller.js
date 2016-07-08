(function() {
    'use strict';

    angular
        .module('cumminsApp')
        .controller('ParticipanteDescripcionDeleteController',ParticipanteDescripcionDeleteController);

    ParticipanteDescripcionDeleteController.$inject = ['$uibModalInstance', 'entity', 'ParticipanteDescripcion'];

    function ParticipanteDescripcionDeleteController($uibModalInstance, entity, ParticipanteDescripcion) {
        var vm = this;

        vm.participanteDescripcion = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ParticipanteDescripcion.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
