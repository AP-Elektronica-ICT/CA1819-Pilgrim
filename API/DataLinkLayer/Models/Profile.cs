using System;
using System.Collections.Generic;
using System.Text;
using static System.Net.Mime.MediaTypeNames;

namespace DataLinkLayer.Models
{
    class Profile
    {
        public int ID { get; set; }

        public string FirstName { get; set; }

        public string LastName { get; set; }

        public string nickName { get; set; }

        public int Age { get; set; }

        public string Country { get; set; }




        public int LeaderboardPosition { get; set; }

        public byte[] ProfilePicture { get; set; }

        public DateTime DateCreated { get; set; }

        public Pilgrimage[] CompletedPilgrimages { get; set; }


    }
}
