'use strict';

describe('Controller Tests', function() {

    describe('Proyecto Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockProyecto, MockParticipante, MockVoluntarioVisitador;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockProyecto = jasmine.createSpy('MockProyecto');
            MockParticipante = jasmine.createSpy('MockParticipante');
            MockVoluntarioVisitador = jasmine.createSpy('MockVoluntarioVisitador');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Proyecto': MockProyecto,
                'Participante': MockParticipante,
                'VoluntarioVisitador': MockVoluntarioVisitador
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
