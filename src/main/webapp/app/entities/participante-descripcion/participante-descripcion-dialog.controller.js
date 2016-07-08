(function() {
    'use strict';

    angular
        .module('cumminsApp')
        .controller('ParticipanteDescripcionDialogController', ParticipanteDescripcionDialogController);

    ParticipanteDescripcionDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'DataUtils', 'entity', 'ParticipanteDescripcion', 'Participante'];

    function ParticipanteDescripcionDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, DataUtils, entity, ParticipanteDescripcion, Participante) {
        var vm = this;

        vm.participanteDescripcion = entity;
        vm.clear = clear;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;
        vm.participantes = Participante.query({filter: 'participantedescripcion-is-null'});
        $q.all([vm.participanteDescripcion.$promise, vm.participantes.$promise]).then(function() {
            if (!vm.participanteDescripcion.participante || !vm.participanteDescripcion.participante.id) {
                return $q.reject();
            }
            return Participante.get({id : vm.participanteDescripcion.participante.id}).$promise;
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
            if (vm.participanteDescripcion.id !== null) {
                ParticipanteDescripcion.update(vm.participanteDescripcion, onSaveSuccess, onSaveError);
            } else {
                ParticipanteDescripcion.save(vm.participanteDescripcion, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('cumminsApp:participanteDescripcionUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


        vm.setLogotipo = function ($file, participanteDescripcion) {
            if ($file && $file.$error === 'pattern') {
                return;
            }
            if ($file) {
                DataUtils.toBase64($file, function(base64Data) {
                    $scope.$apply(function() {
                        participanteDescripcion.logotipo = base64Data;
                        participanteDescripcion.logotipoContentType = $file.type;
                    });
                });
            }
        };

    }
})();
