using BusinessLayer;
using DataLinkLayer;
using DataLinkLayer.Models;
using Microsoft.EntityFrameworkCore;
using System;
using Xunit;

namespace LocationTests
{
    public class LocationTests
    {
        private Context _context;

        public LocationTests()
        {
            _context = new Context(new DbContextOptionsBuilder<Context>().UseInMemoryDatabase().Options);

        }


        [Theory]
        [InlineData("", true)]
        [InlineData(" ", false)]
        [InlineData("test", false)]
        public void IsEmptyTest(string input, bool expected)
        {
            LocationService locationService = new LocationService(_context);
            bool result = locationService.isEmpty(input);
            Assert.Equal(result, expected);
        }

        [Theory]
        [InlineData(5 , false)]
        [InlineData(8 , false)]
        [InlineData(null , true)]
        public void IsNullTest(double? input, bool expected)
        {
            LocationService locationService = new LocationService(_context);
            bool result = locationService.isNull(input);
            Assert.Equal(result, expected);
        }


        [Theory]
        [InlineData("Robin", true)]
        [InlineData("robin", true)]
        [InlineData("Issam", true)]
        [InlineData("Midas", false)]
        public void UserNameAlreadyTakenTest(string input, bool expected)
        {
            var profiles = _context.Profiles;
            ProfileService profileService = new ProfileService(_context);
            bool result = profileService.UserNameAlreadyTaken(input);
            Assert.Equal(result, expected);
        }



    }
}
