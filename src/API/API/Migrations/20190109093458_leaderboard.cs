using Microsoft.EntityFrameworkCore.Migrations;

namespace API.Migrations
{
    public partial class leaderboard : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<string>(
                name: "FireBaseID",
                table: "Pilgrimages",
                nullable: true);

            migrationBuilder.AddColumn<string>(
                name: "username",
                table: "Pilgrimages",
                nullable: true);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "FireBaseID",
                table: "Pilgrimages");

            migrationBuilder.DropColumn(
                name: "username",
                table: "Pilgrimages");
        }
    }
}
