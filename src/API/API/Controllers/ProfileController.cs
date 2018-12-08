using BusinessLayer;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using DataLinkLayer.Models;

namespace API.Controllers
{
    [Route("api/profiles")]
    public class ProfileController : Controller
    {
        private readonly ProfileService profileService;

        public ProfileController(ProfileService profileService)
        {
            this.profileService = profileService;
        }

        [HttpGet]
        public IActionResult GetProfiles()
        {
            return Ok(profileService.GetProfiles());
        }

        [HttpPost]
        public IActionResult Profile([FromBody] Profile newProfile)
        {
            Profile profile = profileService.AddProfile(newProfile);
            if (profile == null)
                return NotFound();
            return Ok(newProfile);
        }


        [HttpGet("{id}")]
        public IActionResult GetProfile(int id)
        {
            Profile profile = profileService.GetProfile(id);
            if (profile == null)
                return NotFound();
            return Ok(profile);
        }

     

        [HttpDelete("{id}")]
        //Geen idee of er post/delete nodig is? Nieuwe accounts worden aangemaakt via
        //FireBase en acc's verwijderen doen wij enkel

        [HttpPut]
        public IActionResult UpdateProfile([FromBody] Profile updatedProfile)
        {
            if (this.profileService.UpdateProfile(updatedProfile) == false)
                return NotFound();
            return Ok(updatedProfile);
        }

    }
}