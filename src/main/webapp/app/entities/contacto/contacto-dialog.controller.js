(function() {
    'use strict';

    angular
        .module('cumminsApp')
        .controller('ContactoDialogController', ContactoDialogController);

    ContactoDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Contacto', 'Participante'];

    function ContactoDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Contacto, Participante) {
        var vm = this;

        vm.contacto = entity;
        vm.clear = clear;
        vm.save = save;
        vm.participantes = Participante.query({filter: 'contacto-is-null'});
        $q.all([vm.contacto.$promise, vm.participantes.$promise]).then(function() {
            if (!vm.contacto.participante || !vm.contacto.participante.id) {
                return $q.reject();
            }
            return Participante.get({id : vm.contacto.participante.id}).$promise;
        }).then(function(participante) {
            vm.participantes.push(participante);
        });

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.contacto.id !== null) {
                Contacto.update(vm.contacto, onSaveSuccess, onSaveError);
            } else {
                Contacto.save(vm.contacto, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('cumminsApp:contactoUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
