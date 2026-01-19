// Test API endpoint
async function testApi() {
    const responseElement = document.getElementById('api-response');
    
    try {
        responseElement.textContent = 'Loading...';
        
        const response = await fetch('/api/health');
        const data = await response.json();
        
        responseElement.textContent = JSON.stringify(data, null, 2);
    } catch (error) {
        responseElement.textContent = `Error: ${error.message}`;
    }
}

// Add page load animation
document.addEventListener('DOMContentLoaded', function() {
    const techCards = document.querySelectorAll('.tech-card');
    
    techCards.forEach((card, index) => {
        setTimeout(() => {
            card.style.opacity = '0';
            card.style.transform = 'translateY(20px)';
            card.style.transition = 'all 0.5s ease';
            
            setTimeout(() => {
                card.style.opacity = '1';
                card.style.transform = 'translateY(0)';
            }, 50);
        }, index * 100);
    });
});
