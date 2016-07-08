'use strict';

describe('Controller Tests', function() {

    describe('Proyecto Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockProyecto, MockVoluntarioVisitador, MockParticipante;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockProyecto = jasmine.createSpy('MockProyecto');
            MockVoluntarioVisitador = jasmine.createSpy('MockVoluntarioVisitador');
            MockParticipante = jasmine.createSpy('MockParticipante');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Proyecto': MockProyecto,
                'VoluntarioVisitador': MockVoluntarioVisitador,
                'Participante': MockParticipante
            };
            createController = function() {
                $injector.get('$controller')("ProyectoDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'cumminsApp:proyectoUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
