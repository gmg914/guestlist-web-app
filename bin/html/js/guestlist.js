var coffeeApp = angular.module('guestListApp', ['ngResource']);

coffeeApp.controller('EventGuestsController', function($scope, $resource, $http, $timeout) {
	var self = this;
	console.log("In EventGuestsController");

	//$scope.refreshGuests();

	//$scope.guests = [
	//	{firstName:'foo', lastName:'bar'}
	//];
	//$http.get('/guestlist/allguests').success(function(data) {
  	//	$scope.guests = data;
	//});
	
	$scope.refreshGuests = function() {
		console.log("In refreshGuests");
		$timeout(function() {
			$http.get('/guestlist/allguests').success(function(data) {
	  			$scope.guests = data;
			});
		},0);
	};
	
	$scope.getAllGuests = function() {
		console.log("In getAllGuests");
		
		$http.get('/guestlist/allguests').success(function(data) {
	  		$scope.allguests = data;
		});
		console.log("allGuests");
		console.log($scope.allguests);
		return $scope.allguests;
	};
	
	$scope.addGuest = function() {
		console.log("In addGuest");
		
		console.log("Current Guests");
		console.log($scope.guests);
		
		//var NewGuest = $resource( '/guestlist/guest/' );
		//NewGuest.save($scope.guest);
		
		$http.post('/guestlist/guest/', $scope.guest)
        	.success(function(data) {
                //console.log(data);
                var updatedGuests = $scope.getAllGuests();
        		console.log("updatedGuests");
        		console.log(updatedGuests);
        		$scope.guests = updatedGuests;
            })
            .error(function(data) {
                console.log('Error: ' + data);
            });
            
        //$timeout(function() {
        //	$scope.guests = $scope.getAllGuests();
        //}, 0);
        

	};

	//$scope.refreshGuests()
	$scope.guests = $scope.getAllGuests();
});