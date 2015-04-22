var coffeeApp = angular.module('guestListApp', ['ngResource']);

coffeeApp.controller('EventGuestsController', function($scope, $resource, $http, $timeout) {
	console.log("In EventGuestsController");
	
	//$scope.refreshGuests = function() {
	//	console.log("In refreshGuests");
	//	$timeout(function() {
	//		$http.get('/guestlist/allguests').success(function(data) {
	//  			$scope.guests = data;
	//		});
	//	},0);
	//};
	
	$scope.initializeGuests = function() {
		console.log("In initializeGuests");
		
		$http.get('/guestlist/allguests').success(function(data) {
	  		$scope.guests = data;
		});
	};
	
	$scope.addGuest = function() {
		console.log("In addGuest");
		
		console.log("Current Guests");
		console.log($scope.guests);
				
		$http.post('/guestlist/guest/', $scope.guest)
        	.success(function(data) {
        		console.log("Post data");
                console.log(data);
        		$scope.guests = data;
            })
            .error(function(data) {
                console.log('Error: ' + data);
            });
	};

	//initialize guests
	$scope.initializeGuests();
});

coffeeApp.controller('EventController', function($scope, $http) {    

    $scope.createEvent = function() {
        $http.post('/event/', $scope.event)
            .success(function(data) {
                $scope.event = data;
                $scope.eventCreated = true;                
            })
            .error(function(data) {
                console.log('Error: ' + data);
            });
    };
    
    $scope.updateSubEvents = function() {
        console.log("In updateSubEvents");
        
        var myUrl = '/event/subevent/' + $scope.event.eventKey;
        $http.get(myUrl).success(function(data) {
            $scope.subevents = data;
        });
    };

    $scope.addSubEvent = function() {
    
        $scope.subEvent.parentEvent = $scope.event.eventKey;

        $http.post('/event/subevent/', $scope.subEvent)
            .success(function(data) {
                $scope.subEvent = data;
                $scope.updateSubEvents();
            })
            .error(function(data) {
                console.log('Error: ' + data);
            });
    };
});