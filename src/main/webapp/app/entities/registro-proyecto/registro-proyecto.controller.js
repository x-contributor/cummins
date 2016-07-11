(function () {
    'use strict';

    angular
            .module('cumminsApp')
            .controller('RegistroProyectoController', RegistroProyectoController);

    RegistroProyectoController.$inject = ['$scope', '$q', 'DataUtils', 'ParticipanteDescripcion', 'Participante', 'Contacto', 'Auth', '$translate'];

    function RegistroProyectoController($scope, $q, DataUtils, ParticipanteDescripcion, Participante, Contacto, Auth, $translate) {
        var vm = this;

        vm.participanteDescripcion = null;
        vm.participante = null;
        vm.contacto = null;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = registerUser;
        vm.error = false;
        vm.terminado = null;
        vm.registerAccount = {};

        function registerUser() {
            vm.registerAccount.langKey = $translate.use();
            vm.registerAccount.login = vm.contacto.email;
            vm.registerAccount.email = vm.contacto.email;
            vm.errorUserExists = null;
            vm.errorEmailExists = null;

            Auth.createAccount(vm.registerAccount).then(function () {
                vm.success = 'OK';
                vm.error = false;
                saveParticipante();
            }).catch(function (response) {
                vm.success = null;
                vm.error = true;
                if (response.status === 400 && response.data === 'login already in use') {
                    vm.errorUserExists = 'ERROR';
                } else if (response.status === 400 && response.data === 'e-mail address already in use') {
                    vm.errorEmailExists = 'ERROR';
                } 
            });
        }

        function save() {
            vm.isSaving = true;
            console.log(vm.participanteDescripcion);
            if (vm.participanteDescripcion.id !== null) {
                ParticipanteDescripcion.update(vm.participanteDescripcion, onSaveSuccess, onSaveError);
            } else {
                ParticipanteDescripcion.save(vm.participanteDescripcion, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess(result) {
            vm.isSaving = false;
            vm.error = false;
            vm.terminado = true;
        }

        function onSaveError() {
            vm.isSaving = false;
            vm.error = true;
            vm.terminado = true;
        }

        function onSaveSuccessParticipante(result) {
            vm.participanteDescripcion.participante = result;
            saveContacto();
        }

        function onSaveSuccessContacto(result) {
            vm.isSaving = false;
            save();
        }

        function saveParticipante() {
            vm.isSaving = true;
            console.log(vm.participante);
            Participante.save(vm.participante, onSaveSuccessParticipante, onSaveError);
        }

        function saveContacto() {
            vm.isSaving = true;
            vm.contacto.participante = vm.participanteDescripcion.participante;
            console.log(vm.contacto);
            Contacto.save(vm.contacto, onSaveSuccessContacto, onSaveError);
        }


        vm.setLogotipo = function ($file, participanteDescripcion) {
            if ($file && $file.$error === 'pattern') {
                return;
            }
            if ($file) {
                DataUtils.toBase64($file, function (base64Data) {
                    $scope.$apply(function () {
                        participanteDescripcion.logotipo = base64Data;
                        participanteDescripcion.logotipoContentType = $file.type;
                    });
                });
            }
        };
    }
})();
