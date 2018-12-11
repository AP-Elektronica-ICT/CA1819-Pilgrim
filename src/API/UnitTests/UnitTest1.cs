using BusinessLayer;
using DataLinkLayer;
using DataLinkLayer.Models;
using Microsoft.EntityFrameworkCore;
using System;
using Xunit;

namespace UnitTests
{
    public class LocationTests
    {
        private Context _context;

        public LocationTests()
        {
            _context = new Context(new DbContextOptionsBuilder<Context>().UseInMemoryDatabase().Options);
            Profile profile1 = new Profile()
            {
                NickName = "Robin",
                base64 = "",
                //Age = 20,
                //Country = "Belgium",
                fireBaseID = "1",
                DateOfBirth = 1,
                FirstName = "Robin",
                LastName = "Laevaert",
                //DateCreated = new DateTime()
            };
            Profile profile2 = new Profile()
            {
                NickName = "Issam",
                base64 = "",
                //Age = 24,
                //Country = "Belgium",
                DateOfBirth = 1,
                fireBaseID = "2",
                FirstName = "Issam",
                LastName = "Moussaid",
                //DateCreated = new DateTime()
            };
            _context.Profiles.Add(profile1);
            _context.Profiles.Add(profile2);
            _context.SaveChanges();
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
        [InlineData(5, false)]
        [InlineData(8, false)]
        [InlineData(null, true)]
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
        public void UserNameAlreadyTaken(string input, bool expected)
        {
            var profiles = _context.Profiles;
            ProfileService profileService = new ProfileService(_context);
            bool result = profileService.UserNameAlreadyTaken(input);
            Assert.Equal(result, expected);
        }
    }
}
