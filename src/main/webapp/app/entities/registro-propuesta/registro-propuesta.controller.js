(function () {
    'use strict';

    angular
            .module('cumminsApp')
            .controller('RegistroPropuestaController', RegistroPropuestaController);

    RegistroPropuestaController.$inject = ['$scope', '$http', 'Principal', 'Proyecto', 'Respuesta', 'Participante', '$window'];

    function RegistroPropuestaController($scope, $http, Principal, Proyecto, Respuesta, Participante, $window) {
        var vm = this;

        vm.participanteDescripcion = null;
        vm.participante = null;
        vm.contacto = null;
        
        vm.error = false;
        vm.terminado = null;
        vm.propuesta = null;
        vm.enviarPropuesta = enviarPropuesta;
        vm.objetivosEspecificos = [];
        vm.grupoBeneficiados = [];
        vm.agregarObjetivoEspecifico = agregarObjetivoEspecifico;
        vm.agregarGrupoBeneficiados = agregarGrupoBeneficiados;
        vm.agregarAcciones = agregarAcciones;
        vm.contadorObjetivosEspecificos = 0;
        vm.contadorGrupoBeneficiados = 0;
        vm.contadoresObjetivosAcciones = [];
        vm.min = 0;
        vm.max = 100;
        vm.email = "";
        vm.contacto = {};
        vm.proyecto = {
            proyecto: null,
            tematica: null,
            tipoProyectto: null,
            id: null
        };
        vm.respuesta = {};
        vm.imprimir = imprimir;

        Principal.identity().then(function (account) {
            vm.email = account.email;
            vm.proyecto.participante;
        });
        
        function obtenerContacto() {
            $http({
                method: 'GET',
                url: '/api/contactos/email/' + vm.email
            }).then(function successCallback(response) {
                vm.contacto = response.data;
                obtenerParticipante(vm.contacto.participante.id);
                guardarProyecto();
            }, function errorCallback(response) {
                console.log("ERROR");
                vm.terminado = true;
            });
        }
        
        function obtenerParticipante(id){
            Participante.get({
                id : id
            }).$promise.then(function(data){
                vm.proyecto.participante = data;
            });
        }
        
        function guardarProyecto(){
            if (vm.proyecto.id !== null) {
                Proyecto.update(vm.proyecto, onSaveSuccess, onSaveError);
            } else {
                Proyecto.save(vm.proyecto, onSaveSuccess, onSaveError);
            }
        }
        
        function guardarRespuesta(){
            vm.respuesta.id = null;
            vm.respuesta.respuesta = JSON.stringify(vm.propuesta);
            vm.respuesta.proyecto = vm.proyecto;
            Respuesta.save(vm.respuesta, onSaveSuccessRespuesta, onSaveErrorRespuesta);
        }
        
        function onSaveSuccessRespuesta(){
            vm.error = false;
            vm.terminado = true;
            //console.log("OK RESPUESTA");
        }
        
        function onSaveErrorRespuesta(){
            vm.error = true;
            vm.terminado = true;
            //console.log("ERROR RESPUESTA");
        }
        
        function onSaveSuccess(result) {
            //console.log("OK PROYECTO");
            vm.proyecto = result;
            guardarRespuesta();
            vm.isSaving = false;
            vm.error = false;
        }

        function onSaveError() {
            //console.log("ERROR PROYECTO");
            vm.isSaving = false;
            vm.error = true;
            vm.terminado = true;
        }
        
        function imprimir(){
            $window.print();
        }

        /**
         * FUNCIIONES PARA MANEJAR LA VISTA
         * @param {type} index
         * @returns {undefined}
         */
        function agregarAcciones(index) {
            vm.objetivosEspecificos[index].acciones.push({
                descripcionAccion: ""
            });
        }

        function agregarGrupoBeneficiados() {
            vm.grupoBeneficiados.push({
                totalBeneficiarios: 0,
                beneficiariosRegulares: 0,
                beneficiariosEventuales: 0,
                hombre: false,
                mujer: false,
                minimo: 0,
                maximo: 100,
                situacion: false,
                discapacidad: {
                    valor: false,
                    comentario: ""
                },
                violencia: false,
                enfermedad: {
                    valor: false,
                    comentario: ""
                },
                abandono: false,
                marginacion: false,
                desnutricion: false,
                otra: {
                    valor: false,
                    comentario: ""
                }
            });
            vm.contadorGrupoBeneficiados++;
        }

        function agregarObjetivoEspecifico() {
            vm.objetivosEspecificos.push({
                objetivo: "",
                actividadSecundaria: "",
                principalBeneficio: "",
                quienes: "",
                donde: "",
                como: "",
                acciones: [{
                        descripcionAccion: ""
                    }]
            });
            vm.contadorObjetivosEspecificos++;
        }

        function enviarPropuesta() {
            vm.propuesta.seccion21 = {};
            vm.propuesta.seccion21.objetivosEspecificos = [];
            vm.propuesta.seccion21.objetivosEspecificos = vm.objetivosEspecificos;
            console.log(vm.propuesta);
            console.log(JSON.stringify(vm.propuesta));
            obtenerContacto();
        }

    }
})();
